var tm,db='',doclist={},issns={};
var ie=document.all?1:0;
var sid=tmp.replace(/.*?(\/F\/.*)/,'$1');if(sid.match("http"))sid="";
function g(id){return document.getElementById(id);}
function w(a){document.write(a);}
function t(a){return I18N["cgi"][a]||a;}
function w2(a){w(I18N["cgi"][a]||a);}
function l(a){if(!I18N["lib"][a])a=a.replace(/\d+/,'');return I18N["lib"][a]||a;}
function GetCookieVal(offset){
    var endstr = document.cookie.indexOf (";", offset);
    if (endstr == -1)
        endstr = document.cookie.length;
    return unescape(document.cookie.substring(offset, endstr));
}
function SetCookie(name, value){
    var expdate = new Date();
    var argv = SetCookie.arguments;
    var argc = SetCookie.arguments.length;
    var expires = (argc > 2) ? argv[2] : null;
    var path = (argc > 3) ? argv[3] : null;
    var domain = (argc > 4) ? argv[4] : null;
    var secure = (argc > 5) ? argv[5] : false;
    if(expires!=null) expdate.setTime(expdate.getTime() + ( expires * 1000 ));
    document.cookie = name + "=" + escape (value) +((expires == null) ? "" : ("; expires="+ expdate.toGMTString()))
        +((path == null) ? "" : ("; path=" + path)) +((domain == null) ? "" : ("; domain=" + domain))
        +((secure == true) ? "; secure" : "");
}
function delCookie(name){
	var exp = new Date();
	exp.setTime (exp.getTime() - 1);
	var cval = getCookie (name);
	document.cookie = name + "=" + cval + "; expires="+ exp.toGMTString();
}
function getCookie(name)
{
	var arg = name + "=";
	var alen = arg.length;
	var clen = document.cookie.length;
	var i = 0;
	while (i < clen){
		var j = i + alen;
		if (document.cookie.substring(i, j) == arg)
			return GetCookieVal (j);
		if(document.cookie.indexOf(" ", i) ==-1)break;
	}
	return null;
}
function XMLRequst(purl,method,asy,mtype){
	if(typeof method =='undefined') method = 'get';
	if(typeof asy =='undefined') asy = true;
	if(typeof mtype =='undefined') mtype = true;
	
	this.http_request = false;
	this.url=purl;
	this.createXMLRequst = function(){
		if(window.XMLHttpRequest) { //Mozilla 浏览器
			this.http_request = new XMLHttpRequest();
			if (this.http_request.overrideMimeType) {//设置MiME类别
				this.http_request.overrideMimeType(mtype);
			}
		}
		else if (window.ActiveXObject) { // IE浏览器
			try {
				this.http_request = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					this.http_request = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e) {}
			}
		}
		if(!this.http_request) { // 异常，创建对象实例失败
			throw new Error("不能创建XMLHttpRequest对象实例.");
			return null;
		}
		this.http_request.open(method,this.url,asy);
		return this.http_request;
	}
}
function ajaxget(name){
    var url="/opac_lcl_chi/"+name;
	var request = new XMLRequst(url,"get").createXMLRequst();
	request.onreadystatechange = function(){
		if (request.readyState == 4){
			if (request.status == 200){
				g(name).innerHTML=request.responseText;
			}
		}
	};
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(null);
}
function getPos(oElement){ 
   var pos = {x:0,y:0}; 
   if( !oElement ) return pos; 
   pos.x = oElement.offsetLeft; 
   pos.y = oElement.offsetTop; 
   while( oElement.offsetParent ){
    pos.x += oElement.offsetParent.offsetLeft; 
    pos.y += oElement.offsetParent.offsetTop; 
    oElement = oElement.offsetParent; 
   } 
   return pos; 
}
function shifttab(obj) {
    var tmpobj = obj.parentNode;
    var taba = tmpobj.childNodes;
    for(i = 0; i < taba.length; i++) {
        if(taba[i].className == "check") {taba[i].className="";g(taba[i].getAttribute("content")).style.display='none';}
    }
    obj.className = "check";
    g(obj.getAttribute("content")).style.display='';
}
function showrating(num){
 	var starnum=Math.floor(num);
 	var halfstar=Math.floor(2*(num-starnum));
 	var stars='';
 	for(var i=0;i<starnum;i++){stars+='★';}
 	if(halfstar){stars+='☆';}
    if(stars==''){stars='☆☆☆';}
    return '<span class=ratingstar>'+stars+'</span>';
}
if(self.location!=parent.location && parent.location.href.indexOf("my.snnu")==-1){
	w("<style>#copyright,#logo,#header,#sides,#lookup,#advanced,#cls,#col1{display:none}#search td{padding:7px 0}#localbase{display:none}#bigcover{display:none}<\/style>");
}
function getsug(){
	obj=g("reqterm")||g("cclterm");
	if(obj.value=='')return;
	g('sug').style.left=getPos(obj).x+'px';
	g('sug').style.top=getPos(obj).y+obj.offsetHeight+'px';
	g('sug').style.width=obj.offsetWidth-2+'px';
	var script = document.createElement('script');
	script.setAttribute('src', '/cgi-bin/sug.cgi?q='+encodeURI(obj.value));
	document.documentElement.lastChild.appendChild(script);
}
var sugno=0;var sugall=0;var curinput='';
function shiftsug(sno){
    var tobj=g('cclterm')||g('reqterm');
    if(sno==-1){sno=sugall;}
    if(sno>sugall){sno-=sugall+1;}
    if(g('sug'+sugno))g('sug'+sugno).className="";
    if(sno==0){tobj.value=curinput;}
    else{
      g('sug'+sno).className="checked";
      tobj.value=g('sug'+sno).lastChild.innerHTML;
    }
    sugno=sno;
}
function aleph_sug(json){
    var num=1;
    var tobj=g('cclterm')||g('reqterm');
	g('sug').innerHTML="";
    curinput=tobj.value;
	for(var key in json){
        g('sug').innerHTML+="<div id=sug"+num+" onmouseover='this.className=\"checked\";sugno="+num+"' onmouseout='this.className=\"\"' onclick='var term=g(\"reqterm\")||g(\"cclterm\");term.value=this.lastChild.innerHTML;term.focus();' style='padding-left:4px'><span class=times>"+json[key]+" "+I18N["hint"]["times"]+"&nbsp;</span><span>"+key+"</span></div>";
        num++;
    }
    sugall=num-1;
	if(g('sug').innerHTML==''){g('sug').style.display='none';return;}
	g('sug').innerHTML+="<div class=close>["+I18N["hint"]["close"]+"]<\/div>";
	g('sug').style.display='';
    //if(ie&&g("format"))g("format").style.display="none";
	//if(ie&&g("sort"))g("sort").style.display="none";
}
function shiftlng(lng){
    var pu=0;
    if(location.href.toLowerCase().indexOf("option-update-lng")!=-1)pu=1;
    var url=location.href.replace(/#$/,"").replace(/(\?|&)(P_)*CON_LNG=[^&]*/i,"$1"+(pu?'P_':'')+"CON_LNG="+lng);
    if(url.indexOf("CON_LNG")==-1)url+=(url.indexOf("?")==-1?'?':'&')+(pu?'P_':'')+"CON_LNG="+lng;
    //var url=location.href.replace(/#$/,"").replace(/(\?|&)(P_)*CON_LNG=[^&]*/i,"$1"+"CON_LNG="+lng);
    //if(url.indexOf("CON_LNG")==-1)url+=(url.indexOf("?")==-1?'?':'&')+"CON_LNG="+lng;
    location.href=url;
    return false;
}
function parseccl(ccl){
    if(ccl.match(/ (AND|OR|NOT) /))return "";
    return ccl.replace(/^SYS = /,'').replace(/[A-Z]+\s*=\s*\(\s*(.*?)\s*\)/g,"$1");
}
var fmtmap={"Monograph":"BK","Conference":"SE","Computer file":"ER"};
