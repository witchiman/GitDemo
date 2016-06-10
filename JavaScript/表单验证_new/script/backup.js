<script language=JavaScript>
function checkform() {
if( !(form.gender7605060[0].checked || form.gender7605060[1].checked) ) {
alert("\请选择性别 !!")
return false;
}

if ( form.year.value.length!=4 
  || isNumberString(form.year.value,"1234567890")!=1 
  || form.year.value<1902 || form.year.value>2004 ) {
alert("\请您输入正确的生日年份!!")
form.year.focus()
return false;
}

if ((form.month.value==0||
form.day.value==0||
(Math.abs(Math.abs(form.month.value*2-15)-5)==2&&(form.day.value==31))||
(form.month.value==2&&((form.day.value>28&&parseInt(form.year.value)%4!=0)||(form.day.value>29&&parseInt(form.year.value)%4==0)))))
{
   alert("请您填写正确的出生日期，注意闰年和大小月！");
   form.year.focus()
   return false;
}
if (!ckidtype(form.idType.selectedIndex)){
   form.idType.focus()
   return false;
}
if ( !ckidnum(form.idType.selectedIndex,form.idCard.value)){
  form.idCard.focus()
  return false;
}
if (form.radomPass.value.length<4){
	alert("\请您输入正确的校验码，该步骤有利于防止注册机 !!")
	form.radomPass.focus()
	return false;
}
var secmail=form.email.value;
if (secmail.length != 0){
	if( secmail.length<8 || secmail.length>64 || !validateEmail(secmail) ) {
		alert("\请您输入正确的邮箱地址 !!")
		form.email.focus()
		return false;
	}
	var mymail=form.username.value+"@163.com";
	if( form.email.value == mymail) {
		alert("\该邮箱地址还没有开通，请您换一个 !!")
		form.email.focus()
		return false;
	}
}
return true;
}
</script>