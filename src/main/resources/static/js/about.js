let req = new XMLHttpRequest();
req.open('GET', document.location, false);
req.send(null);
let change = req.getResponseHeader('change');

if(Boolean(change)){
	document.querySelector('body').style.backgroundImage='url(/images/about.jpg)';
	document.querySelector('body').style.backgroundSize='cover';
}
