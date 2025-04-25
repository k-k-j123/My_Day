document.addEventListener('DOMContentLoaded', function () {
    init();
});

async function init() {
    await loadNotes();
    await getusername();
}

async function loadNotes() {
    const token = localStorage.getItem('authToken');

    try {
        const response = await fetch('http://localhost:8080/api/notes/getAllNotes', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Accept': 'application/json'
            }
        });

        if (response.ok) {
            const notes = await response.json();
            renderNotes(notes);
        } else {
            console.error('Failed to load notes:', await response.text());
        }
    } catch (error) {
        console.error('Network error:', error);
    }
}
function renderNotes(notes) {
    const container = document.querySelector('.notes-container');
    container.innerHTML = ''; // Clear existing notes

    notes.forEach(note => {
        const noteElement = document.createElement('div');
        noteElement.classList.add('note');
        noteElement.dataset.noteId = note.noteId;

        const title = document.createElement('h3');
        title.classList.add('note-title');
        title.textContent = note.title;

        const content = document.createElement('p');
        content.classList.add('note-content');
        content.textContent = note.content;

        // Toggle full content on click
        content.addEventListener('click', (e) => {
            e.stopPropagation();
            content.style.webkitLineClamp = 
                content.style.webkitLineClamp ? '' : '8';
        });

        const footer = document.createElement('div');
        footer.classList.add('note-footer');

        const date = document.createElement('span');
        date.classList.add('note-date');
        date.textContent = formatDate(note.date);

        const deleteBtn = document.createElement('button');
        deleteBtn.classList.add('delete-note');
        deleteBtn.innerHTML = '&times;';
        deleteBtn.title = 'Delete note';
        deleteBtn.addEventListener('click', (e) => {
            e.stopPropagation();
            if (confirm('Are you sure you want to delete this note?')) {
                deleteNote(note.noteId);
            }
        });

        footer.appendChild(date);
        footer.appendChild(deleteBtn);
        noteElement.appendChild(title);
        noteElement.appendChild(content);
        noteElement.appendChild(footer);

        container.appendChild(noteElement);
    });
}

async function deleteNote(noteId) {
    const token = localStorage.getItem('authToken');

    try {
        const response = await fetch(`http://localhost:8080/api/notes/deleteNote/${noteId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Accept': 'application/json'
            }
        });

        if (response.ok) {
            await loadNotes(); // Refresh the notes list
        } else {
            console.error('Failed to delete note:', await response.text());
            alert('Failed to delete note.');
        }
    } catch (error) {
        console.error('Network error:', error);
        alert('Error deleting note. Try again.');
    }
}

function formatDate(isoDate) {
    const date = new Date(isoDate);
    return date.toLocaleString(undefined, {
        dateStyle: 'medium',
        timeStyle: 'short'
    });
}

document.addEventListener('DOMContentLoaded', function () {
    init();

    // Bind modal events
    document.querySelector('.create-note').addEventListener('click', () => {
        document.getElementById('note-modal').classList.remove('hidden');
    });

    document.getElementById('cancel-note').addEventListener('click', () => {
        closeModal();
    });

    document.getElementById('save-note').addEventListener('click', async () => {
        const title = document.getElementById('note-title').value.trim();
        const content = document.getElementById('note-content').value.trim();
        if (!title || !content) return alert('Title and content are required.');

        await createNote(title, content);
        closeModal();
        await loadNotes();
    });
});

function closeModal() {
    document.getElementById('note-title').value = '';
    document.getElementById('note-content').value = '';
    document.getElementById('note-modal').classList.add('hidden');
}

async function createNote(title, content) {
    const token = localStorage.getItem('authToken');

    try {
        const response = await fetch('http://localhost:8080/api/notes/new', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({ title, content })
        });

        if (!response.ok) {
            const errorText = await response.text();
            console.error('Failed to create note:', errorText);
            alert('Failed to create note.');
        }
    } catch (error) {
        console.error('Network error:', error);
        alert('Error creating note. Try again.');
    }
}


async function getusername(){
    const token = localStorage.getItem('authToken');

    try {
        const response = await fetch('http://localhost:8080/api/users/me', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Accept': 'application/json'
            }
        });

        if(response.ok){
            var user = await response.json();
            var email = user.email;
            const username = email.split('@')[0];
            document.getElementById("whoami").innerHTML = username;
        }
        else{
            console.error('Failed to load username:', await response.text());
        }
    }
    catch (error) {
        console.error('Network error:', error);
    }
}

function logout() {
    localStorage.removeItem('authToken');
    window.location.href = '/login';
}