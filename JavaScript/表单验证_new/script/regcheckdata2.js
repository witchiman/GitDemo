


function isNumberString (InString,RefString){
	if(InString.length==0) return (false);
	for (Count=0; Count < InString.length; Count++){
		TempChar= InString.substring (Count, Count+1);
		if (RefString.indexOf (TempChar, 0)==-1)  
		return (false);
	}
	return (true);
}

function isIDString (idnum)
{
	var re=/^[\w][\w-.]*[\w]$/i;
	if(re.test(idnum))
		return true;
	else
		return false;
}

function ckid(idtype,idnum){
	if ( idtype(idtype) )
	   return ckidnum(idnum);
	else
		return false;
}

function ckidtype(idtype){
	
  if ( idtype.value < 0 || idtype.value > 3 ){
    alert("\请您选择正确的证件类别 !!")
    return false;
  }
  return true;
}

function ckidnum(idtype,idnum){
	if ( idnum.length == 0 ){
		   alert("\请您输入证件号码 !!")
		   return false;
	}
	
	var same = true
	for (Count=1; Count < idnum.length; Count++){
		if (  idnum.substring (Count, Count+1) != idnum.substring (Count-1, Count) )  {
		     same=false
		     break;
		}
	}
	if ( same ){
		 alert("\证件号码错误 !!")
		 return false;
	}
	
	if ( idtype == 0){
		if( idnum.length !=15 && idnum.length !=18 ) {
		   alert("\请您输入15/18位的正确身份证号 !!")
		   return false;
	    }
		if( idnum.length ==15 && isNumberString(idnum,"1234567890")!=1){
		   alert("\您输入的身份证号含有非法字符 !!")
		   return false;
		}
		if ( idnum.length ==18 ){
			var cardNo= idnum.substring(0,17);
			if (isNumberString(cardNo,"1234567890")!=1){
				alert("\您输入的身份证号含有非法字符 !!")
				return false;
			}
			cardNo= idnum.substring(17,18);
			if (isNumberString(cardNo,"1234567890Xx")!=1){
				alert("\您输入的身份证号含有非法字符 !!")
				return false;
			}
		}
  }else{
		if( idnum.length <6 || idnum.length >18 ) {
			alert("\请您输入有效证件号码，证件号长度在6－18间 !!")
			return false;
		}
		if (!isIDString(idnum)){
			alert("\有效的证件号码号只能有数字、字母组成!!")
			return false;
		}
  }
  return true;
}