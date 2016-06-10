//定义一个数组,nodeitems,所有nodeitem的容器
nodeitems = new Array() ;
//根节点
tv_topnodeitem = null ;

//=============================================================================================
//函数,添加一个节点,
//参数1:父节点key,
//参数2:节点key
//参数3:节点显示内容
//参数4:节点选用图片
function addNode(parentkey , key , lable , img){
    return new nodeitem( parentkey , key , lable , img ) ;
}
//=============================================================================================
// 显示树,调用根结点的show方法
function showTV()  {
     tv_topnodeitem.show() ;
}
//=============================================================================================
//定义类:nodeitem
//lable:内容
//key:key
//parent:父亲
//img:图片
//subitems:子节点们,是数组
//maxsubitem:最后一个节点
//tag:暂时不用
//status------------------------1:two-direction       0:nobox       0: disactivite
//                              2:three-0direction    1:close-box   1: activite
//                                                    2:open-box    
//第一位:是自己父亲的最后一个儿子,则2个方向.如果自己不是父亲的最后一个儿子,则3个方向
//第二位:没有儿子,0,否则是加号或者减号
//第三位:暂时没用
//一个function就是一个类。当你编写如下function时，其实就是定义了一个类，该function就是它的构造函数。                
function nodeitem( parentkey , key , lable , img )  {
    this.lable = lable ;
	this.key = key ;
    this.parent = findNode( parentkey ) ;
	//如果找到父节点
	if( this.parent != null )  {
	    //得到父亲节点的状态
		aa = this.parent.status ;
		//如果父亲节点的状态==0,也就是说如果父亲现在没有儿子的话,
	    if( aa.substring( 1 , 2 ) == "0" )
	        //更新成关闭状态,100-->110
			this.parent.status = aa.substring( 0 , 1 ) + "1" + aa.substring( 2 , 3 ) ;
	    //如果父亲有最后一个节点了,
		if( this.parent.maxsubitem != null ) 
	        //那么父亲的最后一个儿子改成3个方向的竖线,100-->200
			this.parent.maxsubitem.status = "2" + this.parent.maxsubitem.status.substring( 1 , 3 ) ;
        //在父亲的子节点数组中,添加自己
		this.parent.subitems[ this.parent.subitems.length ] = this ;
        //让父亲的最后一个字节点,指向自己
		this.parent.maxsubitem = this ; 
    }
	//如果没有找到父节点,
    else  {
	    //并且当前已经有个父节点了,则提示:不能有两个顶项
		if( tv_topnodeitem != null )   {
		     alert( "不能有两个顶项!" ) ;
			 return ;
        }
		//如果没有找到父节点,并且当前没有根,则把自己设置为根-->顶项
        tv_topnodeitem = this ;
    }

	this.img = img ;
	this.tag = null ;
	//自己的状态默认是:两个方向,没有儿子
    this.status = "100" ;
	//new一个数组,准备装自己的儿子们
	this.subitems = new Array() ;
	//目前,尚没有所谓的最后一个儿子
	this.maxsubitem = null ;
	//向nodeitems数组中加入自己,并设置自己的id号,
    this.id = nodeitemRegister( this ) ;
}
//=============================================================================================
//得到父节点
function findNode( key )   {
	pppp = null;
	//遍历所有的nodeitem,如果某一个节点不为空并且key等于传进来的key值,返回改节点
    for( i = 0 ; i < nodeitems.length ; i ++ ) {
	   if( nodeitems[ i ] != null ) {
	      if( nodeitems[ i ].key == key ) {
	         pppp = nodeitems[ i ] ;
		  }
	   }
	}
    return pppp ;
}
//=============================================================================================
//如何在Javascript实现OO编程？恐怕最好的方式就是充分利用prototype(原型)属性
nodeitem.prototype.show = nodeitem_show ;
nodeitem.prototype.boxclick = nodeitem_boxclick ;
nodeitem.prototype.close = nodeitem_close ;
nodeitem.prototype.open = nodeitem_open ;
//=============================================================================================
//各种图片
//空白
treeview_box_0_none = "images/4_clos.gif"  ;
//只有竖线
treeview_box_0_line = "images/4_none.gif" ;
//三个方向有竖线
treeview_box_2_open = "images/2_open.gif" ;
treeview_box_2_none = "images/2_none.gif" ;
treeview_box_2_close = "images/2_clos.gif" ;
//两个方向有竖线
treeview_box_1_open = "images/3_open.gif" ;
treeview_box_1_none = "images/3_none.gif" ;
treeview_box_1_close = "images/3_clos.gif" ;

//=============================================================================================
//类nodeitem的show()方法
function nodeitem_show()  {
	//每个Node一个span,并且id=preface0
	str = "<span id = \"preface" + this.id + "\"><table border='0' cellspacing='0' cellpadding='0'><tr><td>" ;
    str_f = "" ;
	//如果有父节点,缩进,缩进的时候,需要判断是显示空白,还是竖线,取决于这个父亲是两个方向的还是3个方向的
    for( j = this.parent ; j != null ; j = j.parent )  {
	    //如果这个父亲是2个方向的,显示空白
		if( j.status.substring( 0 , 1 ) == 1 )
		    str_f = "<img src = '" + treeview_box_0_none + "' align='absmiddle' border=0>" + str_f ;
        //否则,显示竖线
		else
		    str_f = "<img src = '" + treeview_box_0_line + "' align='absmiddle' border=0>" + str_f ;
    }
    str = str + str_f ;
	//判断状态码头两位,决定显示加号还是减号
    str += "<img id = 'box" + this.id + "' nodeid = '" + this.id + "' src = '" ;
    switch( this.status.substring( 0 , 2 ) )   {
        case "10" : str += treeview_box_1_none ; break ;
        case "11" : str += treeview_box_1_close ; break ;
        case "12" : str += treeview_box_1_open ; break ;
        case "20" : str += treeview_box_2_none ; break ;
        case "21" : str += treeview_box_2_close ; break ;
        case "22" : str += treeview_box_2_open ; break ;
    }
	//如果是"+"或者是"-",当单击事件发生,调用box_on_click,把自己传进去
    str += "' align='absmiddle' onclick='box_on_click(this)'>" ;
    //如果没有前导图片,就不显示
	if( this.img == "" )
	    str += this.img ;
    //否则,显示前导图片
	else
	    str += "<img src = '" + this.img + "' align='absmiddle' width='16' height='16'>" ;
    //显示lable内容
	str += "</td><td><table border='0' cellspacing='1' cellpadding='1' style='font-size:9pt; color:#333333' id='lablePanel" + this.id + "'><tr><td ondblclick = 'lable_on_dblclick(" + this.id + ")' onclick='lable_on_click(" + this.id + ")' style='cursor:hand' id='f_lablePanel" + this.id + "' nowrap>" + this.lable + "</td></tr></table></td></tr></table>" ;  
	
	//显示下一个SPAN,id=tv_panel_0
	str += "</span><span id = 'tv_panel_" + this.id + "' style='display:" ;
	
	//如果是打开状态,style="DISPLAY: "
	if( this.status.substring( 1 , 2 ) == '2' )
	   str += "" ;
	//否则style="DISPLAY: none"
	else
	   str += "none" ;
	 
    str += "'></span>" ;
	
	//如果上面没有父亲了,找到BODY标签,再BODY标签刚刚开始的位置,加入这个str
	if( this.parent == null )  
  	   for(var i in document.all){
          if (document.all[i].tagName == "BODY"){
		      document.all[i].insertAdjacentHTML( "AfterBegin" , str ) ;
              break;
          }
       }
	//如果有父亲,加入到以父亲的id命名的以tv_panel_打头的SPAN标签的快要结束的位置
	else   
		document.all( "tv_panel_" + this.parent.id ).insertAdjacentHTML( "BeforeEnd" , str ) ;

	//如果有儿子,循环每一个儿子
    for(var m = 0 ; m < this.subitems.length ; m ++ ) {
		   if( this.subitems[ m ] != null )  {
			   this.subitems[ m ].show() ;
	   	 }
	  }
}

//=============================================================================================
//当单击事件发生,首先调用本方法,本方法调用类nodeitem的boxclick()方法
function box_on_click( obj )  {
	nodeitems[ obj.nodeid ].boxclick() ;
}
//=============================================================================================
//类nodeitem的boxclick()方法
function nodeitem_boxclick()  {
     //如果没有加号或者减号,直接返回
	 if( this.status.substring( 1 ,2 ) == "0" )
       return ; 
	 //如果是关闭的状态,则打开
	 if( this.status.substring( 1 ,2 ) == "1" )   
        this.open() ;
	 //如果是打开的状态,则关闭
	 else  
        this.close() ;
}
//=============================================================================================
//类nodeitem的close()方法,关闭节点
function nodeitem_close()  {
	//更新自己的状态
	this.status = this.status.substring( 0 , 1 ) + "1" + this.status.substring( 2 , 3 ) ;
	//
	document.all( "tv_panel_" + this.id ).style.display = "none" ;
   	 eval( "document.all( 'box' + this.id ).src = treeview_box_" + this.status.substring( 0 , 1 ) +"_close" ) ;
}
//=============================================================================================
//类nodeitem的close()方法,打开节点
function nodeitem_open()  {
     this.status = this.status.substring( 0 , 1 ) + "2" + this.status.substring( 2 , 3 ) ;
   	 document.all( "tv_panel_" + this.id ).style.display = "" ;
   	 //document.all( 'box' + this.id ).src = "treeview_box_" + this.status.substring( 0 , 1 ) +"_open";
	 //document.all( 'box' + this.id ).src = eval("treeview_box_" + this.status.substring( 0 , 1 ) +"_open");
	 eval( "document.all( 'box' + this.id ).src = treeview_box_" + this.status.substring( 0 , 1 ) +"_open" ) ;
}

//=============================================================================================
//向nodeitems数组中加入obj
function nodeitemRegister( obj )   {
	//nodeitems数组中加入obj
    nodeitems[ nodeitems.length ] = obj ;
	//alert(nodeitems.length - 1);
	//返回id号
	return nodeitems.length - 1 ;
}

//========完=====================================================================================































/////////////////////////////////////////////////////////////////////////
//暂时不用
///////////////////////////////////////////////////////////////////////////
//added by msb for the move up/down
nodeitem.prototype.moveUp = nodeitem_moveUp;
nodeitem.prototype.moveDown = nodeitem_moveDown;
nodeitem.prototype.refresh = nodeitem_refresh ;
nodeitem.prototype.remove = nodeitem_remove ;
nodeitem.prototype.setTag = nodeitem_setTag ;
nodeitem.prototype.getTag = nodeitem_getTag ;

//========================================
//Envrionment to hold Listeners
//========================================
tv_listeners = new Array() ;
function listener( type , handler )   {
	alert("listener");
	this.type = type ;
	this.handler = handler ;
	this.id = tv_listeners.length ;
	tv_listeners[ tv_listeners.length ] = this ;
}

function addListener( type , handler )  {
	alert("addListener");
    new listener( type , handler ) ;  
 }
//=== END =====

//added by msb for the sort and move up/down
function nodeitem_moveUp() {
	alert("nodeitem_moveUp");
	if (this == tv_topnodeitem) return; //topitem 

	ssubitems = this.parent.subitems;
	for ( i=0; i<ssubitems.length; i++ ) {
		if( ssubitems[i] == this ) {
			break;
		}
	}
	if (i==0) return;
	ssubitems[i] = ssubitems[i-1];
	ssubitems[i-1] = this;
	if (i==ssubitems.length-1) {
		ssubitems[i-1].status = "2" + ssubitems[i-1].status.substring(1, 3);
		ssubitems[i].status = "1" + ssubitems[i].status.substring(1, 3);
	}
	/*
	itemTemp = this;
	ssubitems[this.nodeIndex-1] */
/*	for ( i=0; i<ssubitems.length; i++ ) {
		if( ssubitems[i] != null && ssubitems[i].nodeIndex == (this.nodeIndex-1) )
			previousitem = ssubitems[i]
	}
	previousitem.nodeIndex = this.nodeIndex;
	this.nodeIndex = this.nodeIndex -1;
	swap(this,previousitem);
*/
	//label_on_click(this.id);
	this.parent.refresh();

	lable_on_click(this.id);
}

function nodeitem_moveDown() {
	alert(nodeitem_moveDown)
	if (this == tv_topnodeitem) return; //topitem
	
	ssubitems = this.parent.subitems;
	for ( i=0; i<ssubitems.length; i++ ) {
		if( ssubitems[i] == this ) {
			break;
		}
	}
	if (i==ssubitems.length-1) return;
	ssubitems[i] = ssubitems[i+1];
	ssubitems[i+1] = this;
	if (i==ssubitems.length-2) {
		ssubitems[i+1].status = "1" + ssubitems[i+1].status.substring(1, 3);
		ssubitems[i].status = "2" + ssubitems[i].status.substring(1, 3);
	}

	this.parent.refresh();
		
	lable_on_click(this.id);
}

/*function swap (item1, item2) {
	nodeitems[item1.id] = item2;
	nodeitems[item2.id] = item1;
	idTemp = item1.id;
	item1.id = item2.id;
	item2.id = idTemp;
}*/
function nodeitem_setTag( obj ) {
   alert("nodeitem_setTag");
    this.tag = obj ;
}

function nodeitem_getTag() {
	alert("nodeitem_getTag");
    return this.tag ;
}
//刷新
function nodeitem_refresh()  {
	alert("refresh");
	str = "<table border='0' cellspacing='0' cellpadding='0'><tr><td>" ;
    str_f = "" ;
    for( j = this.parent ; j != null ; j = j.parent )  {
	    if( j.status.substring( 0 , 1 ) == 1 )
		   str_f = "<img src = '" + treeview_box_0_none + "' align='absmiddle'>" + str_f ;
        else
		   str_f = "<img src = '" + treeview_box_0_line + "' align='absmiddle'>" + str_f ;
    }
	str = str + str_f ;
    str += "<img id = 'box" + this.id + "' nodeid = '" + this.id + "' src = '" ;
    switch( this.status.substring( 0 , 2 ) )   {
	        case "10" : str += treeview_box_1_none ; break ;
	        case "11" : str += treeview_box_1_close ; break ;
	        case "12" : str += treeview_box_1_open ; break ;
	        case "20" : str += treeview_box_2_none ; break ;
	        case "21" : str += treeview_box_2_close ; break ;
	        case "22" : str += treeview_box_2_open ; break ;
    }
    str += "' align='absmiddle' onclick='box_on_click(this)'>" ;
    if( this.img == "" )
	    str += this.img ;
    else
	    str += "<img src = '" + this.img + "' align='absmiddle' width='16' height='16'>" ;
    str += "</td><td><table border='0' cellspacing='1' cellpadding='1' style='font-size:9pt; color:#333333' id='lablePanel" + this.id + "'><tr><td ondblclick = 'lable_on_dblclick(" + this.id + ")' onclick='lable_on_click(" + this.id + ")' style='cursor:hand' id='f_lablePanel" + this.id + "' nowrap>" + this.lable + "</td></tr></table></td></tr></table>" ;  
	document.all( "preface" + this.id ).innerHTML = str ;
    document.all( "tv_panel_" + this.id ).innerHTML = "" ;
	for( m = 0 ; m < this.subitems.length ; m ++ )
	   if( this.subitems[ m ] != null )  {
		   userstack.put( m ) ;
		   this.subitems[ m ].show() ;
		   m = userstack.get() ;
       }
}
//删除一个节点
function nodeitem_remove()  {
    pparent = this.parent ;
	if( pparent == null )   {
        removenodeitem( this.id ) ;
   	    for(var i in document.all){
           if (document.all[i].tagName == "BODY")
             {
			   document.all[i].innerHTML = "" ;
               break
             }
          }
		return ;
	}
	lastsubitem = null ;
	for( i = 0 ; i < pparent.subitems.length ; i ++ )
	   if( pparent.subitems[ i ] != null )  
		   if ( pparent.subitems[ i ] == this ) 
		       pparent.subitems[ i ] = null ;
           else
		       lastsubitem = pparent.subitems[ i ] ;

    pparent.maxsubitem = lastsubitem ; 
	if( lastsubitem == null )   
        pparent.status = pparent.status.substring( 0 , 1 ) + "0" + pparent.status.substring( 2 , 3 ) ;
    else   
	    pparent.maxsubitem.status = "1" + pparent.maxsubitem.status.substring( 1 , 3 ) ;
     removenodeitem( this.id ) ;

	//added by msb for move up/down
	arrTemp = new Array();
	j = 0;
	for ( i=0; i<pparent.subitems.length; i++ ) {
		if ( pparent.subitems[i] != null ) {
			arrTemp[j] = pparent.subitems[i];
			j++;
		}
	}
	this.parent.subitems = arrTemp;
	//end added

	 pparent.refresh() ;
	 //tv_topnodeitem.refresh() ;
}
//删除一个节点
function removenodeitem( id )   {
    curitem = nodeitems[ id ] ;
	nodeitems[ id ] = null ;
	for( m = 0 ; m < curitem.subitems.length ; m ++ ) 
	     if( curitem.subitems[ m ] != null )   {
		   userstack.put( m ) ;
		   removenodeitem( curitem.subitems[ m ].id ) ;
		   m = userstack.get() ;
       }
}

//函数:删除一个节点
function deleteNode(  key )   {
    curNode = findNode( key ) ;
	if( curNode == null )
	  return false ;
    curNode.remove() ; 
    return true ;
}

tv_curlable = null ;
tv_curlable_f = null ;


//单击(文字)的时候,调用本方法
function lable_on_click( id )  {
       key = nodeitems[ id ].key ;
       if( nodeitems[ id ].parent == null )
	      parentkey = "" ;
       else
	      parentkey = nodeitems[ id ].parent.key ;
	      	   
	   if( tv_curlable != null )  {
		   tv_curlable.bgColor = "transparent" ;
		   tv_curlable.style.color = "#333333" ;
		   tv_curlable_f.bgColor = "transparent" ;
       }
	       tv_curlable = document.all("lablePanel"+id) ;
		   tv_curlable.bgColor = "#000000" ;
		   tv_curlable.style.color = "#FFFFFF" ;
	       tv_curlable_f = document.all("f_lablePanel"+id) ;
		   tv_curlable_f.bgColor = "#888888" ;

	   for( i = 0 ; i < tv_listeners.length ; i ++ )  
	      if( tv_listeners[ i ].type == "click" )  {
			   h = tv_listeners[ i ].handler ;
			   eval( h + "( '" + key + "' , '" + parentkey + "' ) ; " ) ; 
           }

}
//双击(文字)的时候
function lable_on_dblclick( id ) {
		key = nodeitems[ id ].key ;
       if( nodeitems[ id ].parent == null )
	      parentkey = "" ;
       else
	      parentkey = nodeitems[ id ].parent.key ;
          
	   if( tv_curlable != null )  {
		   tv_curlable.bgColor = "transparent" ;
		   tv_curlable.style.color = "#333333" ;
		   tv_curlable_f.bgColor = "transparent" ;
       }
	       tv_curlable = document.all("lablePanel"+id) ;
		   tv_curlable.bgColor = "#000000" ;
		   tv_curlable.style.color = "#FFFFFF" ;
	       tv_curlable_f = document.all("f_lablePanel"+id) ;
		   tv_curlable_f.bgColor = "#888888" ;
	   for( i = 0 ; i < tv_listeners.length ; i ++ )  
	      if( tv_listeners[ i ].type == "dblclick" )  {
			   h = tv_listeners[ i ].handler ;
			   eval( h + "( '" + key + "' , '" + parentkey + "' ) ; " ) ; 
           }
}