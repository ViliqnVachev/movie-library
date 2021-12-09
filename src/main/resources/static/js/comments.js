let csrfHeaderName = document.head.querySelector('[name="_csrf_header"]').content;
let csrfHeaderValue = document.head.querySelector('[name="_csrf"]').content;
let submitForm = document.querySelector('#postComment');
let movieId = document.querySelector('#movieId').value;
let protocol=document.location.protocol;
let host=document.location.host;
let url =`${protocol}//${host}/api/comments`;
let commentCtnr = document.querySelector('#commentCtnr');
submitForm.addEventListener('click', submitEventHandler);

async function submitEventHandler(e) {
    e.preventDefault();
    try {
        let response = await postComment();
        commentCtnr.prepend(createCommentHtml(response));
        document.querySelector('#message').value = '';
    } catch (error) {
        let errorObj = JSON.parse(error.message);
        if (errorObj.errors) {
            errorObj.errors.forEach(e => {
                let errorElement = e.field;
                if (errorElement) {
                    let element = document.querySelector('#message');
                    element.classList.add("is-invalid");
                }
            }
            )
        }
    }
}

async function postComment() {
    let commentContent = document.querySelector('#message').value;
    let comentObj = { commentContent: commentContent };
    let response = await fetch(`${url}/${movieId}`, {
        method: 'post',
        headers: {
            'Content-type': 'application/json',
            [csrfHeaderName]: csrfHeaderValue
        },
        body: JSON.stringify(comentObj)
    });

    if (!response.ok) {
        let errorMessage = await response.text();
        throw new Error(errorMessage);
    }

    return response.json();
}

async function showComments() {

    let response = await fetch(`${url}/${movieId}`);

    let data = await response.json();

    data.reverse().forEach(comment => {
        let brElement = document.createElement('br');
        commentCtnr.appendChild(brElement);
        commentCtnr.appendChild(createCommentHtml(comment));
    })

}

function createCommentHtml(comment) {
    let divElement = document.createElement('div');
    let hElement = document.createElement('h4');
    let dateElement = document.createElement('h6');
    let pElement = document.createElement('p');
    let brElement = document.createElement('br');
    let hrElement = document.createElement('hr');

    dateElement.textContent = `${comment.created}`;
    hElement.textContent = `${comment.author}`;
    pElement.textContent = comment.commentContent;

    divElement.classList.add('comment');
    divElement.appendChild(hrElement);
    divElement.appendChild(hElement);
    divElement.appendChild(dateElement);
    divElement.appendChild(brElement);
    divElement.appendChild(pElement);

    return divElement;
}

showComments();