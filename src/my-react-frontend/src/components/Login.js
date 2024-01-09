import React, {useEffect, useState} from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { jwtDecode } from "jwt-decode";


function Login({ onLogin }) {
    const [user, setUser] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loginError, setLoginError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        // Initialize Google Sign-In once the component is mounted
        google.accounts.id.initialize({
            client_id: "653829545632-s1tg9di96ernst657soqhvtdt37vssp8.apps.googleusercontent.com",
            callback: handleCallbackResponse,
            // auto_select: true,
        });

        // Render the Google Sign-In button
        google.accounts.id.renderButton(
            document.getElementById("google-login-button"),
            {
                theme: "outline",
                size: "large",
                text: "Sign in with Google",
                shape: "rectangular",
                width: "auto",
                height: 40,
            }
        );

        // Cleanup function when the component is unmounted
        return () => {
            // Perform any cleanup or removal of resources
            // For example, you can remove the Google Sign-In button if needed
        };
    }, []); // The empty dependency array ensures that this effect runs only once when the component mounts


    const handleRegisterClick = () => {
        navigate('/register');
    };

    const handleLoginSubmit = async (e) => {
        e.preventDefault();
        setLoginError(''); // Reset any previous login errors

        try {
            // Prepare the request body and parameters for the authentication request
            const requestBody = {
                name: username,
                password: password,
            };

            const response = await axios.post("/api/users/login", requestBody);
            //alert(response.data);
            //alert(response.status)
            if (response.status === 200) {
                if (response.data)
                {
                    //alert(response.data.id)
                    onLogin(response.data); // Update App state if authentication is successful
                    navigate('/home'); // Navigate to HomePage
                }
            } else {
                throw new Error('Authentication failed'); // Rzuć wyjątek, jeśli autoryzacja nie powiedzie się
            }
        } catch (error) {
            console.error('Login error:', error);
            if (error.response && error.response.status === 404) {
                setLoginError("User not found"); // Ustaw błąd dla błędnej nazwy użytkownika
            } else {
                setLoginError('An error occurred during login'); // Ustaw błąd dla innych błędów
            }
            alert(loginError); // Teraz alert wyświetli aktualny błąd
        }
    }

    function handleCallbackResponse(response) {
        console.log("Encoded JWT ID token: " + response.credential);
        // Handle the Google Sign-In callback response if needed
        // For example, you can use the response to:
        // - Obtain the Google user ID
        // - Obtain an ID token for the user (which may be used with Google services)
        // - See the Google Sign-In documentation for more details
        //alert(response.credential)

        try {
            const userObject = jwtDecode(response.credential);
            console.log("Decoded JWT ID token:", userObject);
            setUser(userObject);
            onLogin(userObject); // Update App state if authentication is successful
            navigate('/home'); // Navigate to HomePage
        } catch (error) {
            console.error("Error decoding JWT:", error);
        }
    }

    return (
        <div className="flex justify-center items-center h-screen">
            <div className="w-full max-w-xs flex flex-col items-center">
                <h2 className="text-3xl mb-2">Login</h2>
                <form onSubmit={handleLoginSubmit} className="w-full flex flex-col mb-4">
                    <input
                        className="myinput mb-2"
                        autoFocus
                        required
                        type="text"
                        placeholder="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                    <input
                        className="myinput mb-2"
                        required
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <button
                        type="submit"
                        className="loginbutton loginbutton-submit"
                    >
                        Login
                    </button>

                </form>

                <button
                    className="loginbutton loginbutton-other w-full mb-4"
                    onClick={handleRegisterClick}
                >
                    Register here
                </button>

                <div id="google-login-button"></div>
            </div>
        </div>
    );
}

export default Login;
