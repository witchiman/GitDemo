<script type="text/javascript">
	var req;
	function validate() {
		var idField = document.getElementById("userId");
		var url = "validate.jsp?id=" + escape(idField.value);
		if(window.XMLHttpRequest) {
			req = new XMLHttpRequest();
		}else if (window.ActiveXObject) {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		}
		req.open("GET,url,true);
		req.onreadystatechange = callback;
		req.send(null);
	}
	
	function callback() {
		if(req.readyState == 4) {
			if(req.status == 200) {
				//deal with the msg
				var msg = req.responseXML.getElementsByTagName("msg")[0];
				setMsg(msg.childNodes[0].nodeValue);
			}
		}
	}
	
	function set Msg(msg) {
		mdiv = document.getElementById("userId");
		if(msg == "invalid") {
			mdiv.innerHTML = "<font color="red">Username exists</font>"
		}else {
			mdiv.innerHTML = "<font color="greeb">Congratulations! you can use this username!</font>"
		}
	}
</script>