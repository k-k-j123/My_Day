document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.querySelector('form[name="form-login"]');
    
    loginForm.addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const email = document.getElementById('user').value;
        const password = document.getElementById('pass').value;
        
        const submitBtn = document.querySelector('input[type="submit"]');
        submitBtn.value = 'Logging in...';
        submitBtn.disabled = true;
        
        try {
            const response = await fetch('http://localhost:8080/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify({
                    email: email,
                    password: password
                })
            });
            
            if (response.ok) {
                const data = await response.json();
                localStorage.setItem('authToken', data.token);
                window.location.href = '/home';
            } else {
                const errorText = await response.text();
                alert('Login failed: ' + errorText);
            }
        } catch (error) {
            alert('Network error. Please try again.');
        } finally {
            submitBtn.value = 'Login';
            submitBtn.disabled = false;
        }
    });
});
