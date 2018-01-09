function duxiu(obj){
        if(obj.DUXIU_URL!=null)
        document.getElementById("DUXIU_"+obj.ISBN).innerHTML = "<a href=" + obj.DUXIU_URL +"><img src=http://202.204.214.94:8991/exlibris/aleph/u20_1/alephe/www_f_chi/icon/duxiu.gif border=0 alt='读秀电子书链接'></a>";
        if(obj.SHUSHENG_URL!=null)
        document.getElementById("SHUSHENG_"+obj.ISBN).innerHTML = "<a href=" + obj.SHUSHENG_URL +"><img src=http://202.204.214.94:8991/exlibris/aleph/u20_1/alephe/www_f_chi/icon/shusheng.gif border=0 alt='书生本地电子书链接'></a>";
}

 function thrd(obj) {
        if(obj.BOOK_DOC_NUMBER){
                        var s="";
         $.ajax({
           type:"POST",
           url:"http://202.204.214.94:8991/X?",
           data:"op=item-data&doc_number="+obj.BOOK_DOC_NUMBER+"&base="+obj.Z05_BASE,
           dataType:"xml",
           success:function(data){
                            var arr_collection= ['*','XYBY','ZKBY3','ZTBY4','ZTBY5','ZTBY6','WTBY7'];


            $(data).find('item').each(function(i){
                                var call_no_1=$(this).children('call-no-1').text();
                var collection=$(this).children('collection').text();
                                var flag=$.inArray(collection,arr_collection);
                if(flag <=0){
                                        return true;
                                }
                                        if(call_no_1== ""){
                                        return true;
                                        }
                s="sub_lib="+collection+"&"+"bn="+call_no_1;
//+"&"+"user=opac"+Math.floor(Math.random()*100000000);

            });
                        if (s == ""){
                        //      alert(s);       
                        s=" ";
                    }else{
                        s="<a href='http://202.204.214.172:8080/CNU/seopac.action?"+s+"' target='_blank'><img src=http://202.204.214.94:8991/exlibris/aleph/u20_1/alephe/www_f_chi/icon/3d_navigater.jpg border=0 alt='3d导航系统'></a>";
                        //alert(obj.BOOK_DOC_NUMBER);
                        $("#3D_"+obj.BOOK_DOC_NUMBER).html(s);
                        //alert(s);
                        }
            }
           });


                  }
        }
function updatapage() 
{ 
     //alert("dddd")
     var thenode = window.document.getElementsByTagName("TABLE"); 
     var child; 
     var childtd,kindexnum; 
     var url='http://202.204.214.120/'; 
     var schoolid="211270";
    // alert(thenode.length)
      childFA=thenode[3].getElementsByTagName("tr"); 
      var count=childFA.length; 
    //  alert(count); 
      if (count>0) 
      { 
         kindexnum="";
         for(m=0;m<childFA.length;m++) 
         { 
            childSon=childFA[m].getElementsByTagName("td"); 
 
            var Contentstr=childSon[0].innerText; 
            var  num=Contentstr.indexOf("ISBN"); 
            if(num>=0) 
            { 
 
              kindexnum=Jtrim(childSon[1].innerText); 
              var kindexnumlist 
              kindexnumlist=kindexnum.split(":"); 
              kindexnum=Jtrim(kindexnumlist[0]); 
              kindexnumlist=kindexnum.split("("); 
              kindexnum=Jtrim(kindexnumlist[0]); 
              break;

            } 
           } 
           if(kindexnum!="")
           {
              if(kindexnum=="") 
                kindexnum="0"; 
              var text = window.document.createTextNode("非书资源 "); //新建一个TextNode节点 
                thea = window.document.createElement("font"); 
                thea.appendChild(text); 
               tempb=window.document.createElement("b"); 
                tempb.appendChild(thea); 
 
                var tr = window.document.createElement("tr");          //新建一个tr类型的Element节点 
                var td1 = window.document.createElement("td");         //新建一个td类型的Element节点 
                     td1.setAttribute("bgcolor","#ffffff",""); 
                     td1.setAttribute('className', 'td1'); 
                     td1.appendChild(tempb); 
 
                var td2 = window.document.createElement("td");         //新建一个td类型的Element节点 
                 td2.setAttribute('className', 'td1'); 
                     td1.setAttribute("bgcolor","#ffffff",""); 
                     var image = window.document.createElement("img"); 
                        // alert(url+'findisoid?indexnum='+kindexnum); 
                     image.setAttribute('src', url+'findisoid?isbn='+kindexnum); 
                     image.setAttribute('width', '20'); 
                     image.setAttribute('height', '20'); 
                     image.setAttribute('border', '0'); 
                     image.setAttribute('alt',kindexnum); 
                     var  thea = window.document.createElement("a");          //新建一个a类型的Element节点 
                     thea.setAttribute("href",url+"openurl?isbn="+kindexnum+"&ID="+GetAsciiStr(schoolid)); 
                     thea.setAttribute("target","_blank"); 
                     thea.appendChild(image); 
                     td2.appendChild(thea); 
                     tr.appendChild(td1); 
                     tr.appendChild(td2); 
                     var xtbody = window.document.createElement("TBODY"); 
                     xtbody.appendChild(tr); 
                     thenode[3].insertBefore(xtbody, null);            
           
           
           }
           
         } 
} 
 function GetAsciiStr(str)
{
  var i;
  var tempstr,tempstr1,resultstr="";
  tempstr1="_"
  
  for(i=0;i<str.length;i++)
  {
    tempstr=str.charAt(i);
    if(i==str.length-1)
      resultstr=resultstr+tempstr.charCodeAt();
    else
      resultstr=resultstr+tempstr.charCodeAt()+tempstr1;
    
  }
  return resultstr;
}

 function Jtrim(str)
{

        var k = 0;
        var len = str.length;
        if ( str == "" ) return( str );
        t= len -1;
        flagbegin = true;
        flagend = true;
        while ( flagbegin == true && k< len)
        {
           if ( str.charAt(k) == " " )
                {
                  k=k+1;
                  flagbegin=true;
                }
                else
                {
                        flagbegin=false;
                }
        }

        while  (flagend== true && t>=0)
        {
            if (str.charAt(t)==" ")
                {
                       t=t-1;
                        flagend=true;
                }
                else
                {
                        flagend=false;
                }
        }

        if ( k > t ) return ("")

        trimstr = str.substring(k,t+1);
        return trimstr;
        
} 