function onDelete() {
    return fetch(location.href, {
        method: 'DELETE'
    })
    .then((response) => {
        if(response.status !== 200) {
            return response.text().then((text) => alert(`Failed to delete Todo: ${text}`));
        }
    })
    .then(() => location.href = '/todo')
    .catch((err) => alert(`Failed to delete Todo: ${err}`));
}

function onTodoCreated() {
    let todo = {
        description: (document.getElementById('description').value || '').trim(),
        completed: document.getElementById('completed').checked
    };
    let url = location.href;

    return fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(todo)
    })
    .then((response) => {
        if(response.status !== 201) {
            return response.text().then((text) => alert(`Failed to create Todo: ${text}`));
        }
    })
    .then(() => location.href = location.href)
    .catch((err) => alert(`Failed to create Todo: ${err}`));
}

function onCompletedChanged(sender) {
    let url = `${location.origin}/todo/${sender.value}`
    let completed = sender.checked;

    return fetch(url, {
        method: 'PATCH',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(completed)
    });
}