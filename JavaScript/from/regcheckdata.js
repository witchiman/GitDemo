function check_empty(text) {
  marks= false;
  var nLengs=text.length;
  for(var i=0;i<nLengs;i++) {
       if(text.substring(i,i+1)!=' ' &&text.substring(i,i+1)!='　') 
       {
          marks= true;
          break;
       }
   }
  return marks;
}


function checkdata() {
	if(!check_empty(form1.from.value)){
		alert("籍贯不能为空");	
		return false;
	}
	if(!check_empty(form1.username.value)){
		alert("不能为空");	
		return false;
	}
	if(!check_empty(form1.password.value)){
		alert("密码不能为空");	
		return false;
	}
	if(!check_empty(form1.password1.value)){
		alert("确认密码不能为空");	
		return false;
	}
	if(form1.password.value!=form1.password1.value){
		alert("两个密码必须相等");	
		return false;
	}
	
	if( !(form1.gender[0].checked || form1.gender[1].checked) ) {
		alert("请选择性别!");
		form1.gender[0].focus();
		return false;
	}
	if(!check_empty(form1.interest.value)){
		alert("兴趣");	
		return false;
	}
	if(!check_empty(form1.intro.value)){
		alert("介绍不能为空");	
		return false;
	}
	flag =false;
	for(var i=0; i<form1.skill.length; i++ ) {
		if(form1.skill[i].checked){
			flag=true;
		}
	}
	if(!flag){
		alert("请选择技能");	
		return false;
	}
	return true;
}