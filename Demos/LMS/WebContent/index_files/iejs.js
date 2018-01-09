
function informationbar(){
	this.displayfreq="always"
	this.content='<a href="javascript:informationbar.close()"><img src="/themes/hiroshige/images/close.jpg" style="width: 14px; height: 14px; float: right; border: 0; margin-right: 5px" /></a>'
}
informationbar.prototype.setContent=function(data){
	this.content=this.content+data
	document.write('<div id="informationbar" style="top: -500px">'+this.content+'</div>')
}
informationbar.prototype.animatetoview=function(){
	var barinstance=this
	if (parseInt(this.barref.style.top)<0){
		this.barref.style.top=parseInt(this.barref.style.top)+5+"px"
		setTimeout(function(){barinstance.animatetoview()}, 50)
	}
	else{
		if (document.all && !window.XMLHttpRequest)
		this.barref.style.setExpression("top", 'document.compatMode=="CSS1Compat"? document.documentElement.scrollTop+"px" : body.scrollTop+"px"')
	else
		this.barref.style.top=0
	}
}
informationbar.close=function(){
	document.getElementById("informationbar").style.display="none"
	if (this.displayfreq=="session")
		document.cookie="infobarshown=1;path=/"
}
informationbar.prototype.setfrequency=function(type){
	this.displayfreq=type
}
informationbar.prototype.initialize=function(){
	if (this.displayfreq=="session" && document.cookie.indexOf("infobarshown")==-1 || this.displayfreq=="always"){
		this.barref=document.getElementById("informationbar")
		this.barheight=parseInt(this.barref.offsetHeight)
		this.barref.style.top=this.barheight*(-1)+"px"
		this.animatetoview()
	}
}

window.onunload=function(){
	this.barref=null   }


var browser=navigator.appName 
var b_version=navigator.appVersion 
var version=b_version.split(";"); 
var trim_Version=version[1].replace(/[ ]/g,""); 

if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE7.0") 
{ var infobar=new informationbar()
infobar.setContent('经检测您使用的浏览器版本较低，为了您更好的浏览图书馆网站，我们建议您使用IE8版本以上浏览器或者火狐(firefox)、谷歌(chrome)浏览器。')
infobar.initialize()
     
    
} 
else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE6.0") 
{    var infobar=new informationbar()
infobar.setContent('经检测您使用的浏览器版本较低，为了您更好的浏览图书馆网站，我们建议您使用IE8版本以上浏览器或者火狐(firefox)、谷歌(chrome)浏览器。')
infobar.initialize() 
      
}




