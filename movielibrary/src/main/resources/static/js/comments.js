let submitBtn = document.querySelector('#postComment');
let movieId = document.querySelector('#movieId').value;
submitBtn.addEventListener('click', submitEventHandler);

function submitEventHandler(e) {
    e.preventDefault();
    let url = 'http://localhost:8080/api/comments'
    let commentContent = document.querySelector('#message').value;
    let comentObj = { message: commentContent };

    fetch(`${url}/${movieId}`, {
        credentials: 'include',
        method: 'post',
        headers: { 'Content-type': 'application/json' },
        body: JSON.stringify(comentObj)
    }).then(response => response.json()).catch(alert('Unable to create comment'));

}