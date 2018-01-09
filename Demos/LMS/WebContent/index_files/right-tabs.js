var Menu_dis=[];//设定默认显示第三,兼作当前显示记录器
var Menu_Scroll=[];//设定默认不循环（0=yes,"no"=no），兼作循环启停设定器
function tab(m,n){
	var titles=document.getElementById("menu"+m).getElementsByTagName("li");
	var contents=document.getElementById("main"+m).getElementsByTagName("li");	
	for(i=0;i<titles.length;i++){
		 titles[i].className=i==n?"hover":"";
 		 contents[i].style.display=i==n?"block":"none";
	}
Menu_dis[m]=n;
}
onload=init;
function init(){
	for (var i=0;i<Menu_dis.length;i++){
		tab(i,Menu_dis[i]);
	}
	setTimeout(Next,5000)
}
function Next(){
	for (var i=0;i<Menu_dis.length;i++){
		if (Menu_Scroll[i]!="no" &&  Menu_Scroll[i]!=1){
			Menu_dis[i]=Menu_dis[i]++>=document.getElementById("main"+i).getElementsByTagName("li").length-1?0:Menu_dis[i]++;
			tab(i,Menu_dis[i]);
		}
	}
	setTimeout(Next,3000)
}
function startScroll(k){
	if (Menu_Scroll[k]!="no")Menu_Scroll[k]=0;
}
function stopScroll(k){
	if (Menu_Scroll[k]!="no")Menu_Scroll[k]=1;
}