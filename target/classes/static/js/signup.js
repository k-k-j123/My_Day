async function handleSignup(event) {
    event.preventDefault();
    
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('http://localhost:8080/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        });

        // First check if response has content
        const text = await response.text();
        let data = {};
        
        try {
            data = text ? JSON.parse(text) : {};
        } catch (e) {
            console.warn('Response was not JSON:', text);
        }

        if (response.ok) {
            // Registration successful, redirect to login page
            window.location.href = '/login';
        } else {
            // Show error message from server or default message
            alert(data.message || text || 'Registration failed. Please try again.');
        }
    } catch (error) {
        console.error('Error during registration:', error);
        alert('Network error during registration. Please try again.');
    }
}

document.addEventListener('DOMContentLoaded', function() {
    const signupForm = document.getElementById('signupForm');
    if (signupForm) {
        signupForm.addEventListener('submit', handleSignup);
    }
});