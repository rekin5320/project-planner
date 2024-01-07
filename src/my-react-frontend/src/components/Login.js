import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Login({ onLogin }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loginError, setLoginError] = useState('');
    const navigate = useNavigate();

    const handleRegisterClick = () => {
        navigate('/register');
    };

    const handleLoginSubmit = async (e) => {
        e.preventDefault();
        setLoginError(''); // Reset any previous login errors

        try {
            // Prepare the request body and parameters for the authentication request
            const requestBody = {
                name: username, // Assuming this is the expected format for your User object
                // ... include other User fields if necessary
            };
            const requestParams = {
                params: { enteredPassword: password }
            };

            // Make the POST request to the authenticate endpoint
            const response = await axios.post("/api/users/authenticate", requestBody, requestParams);

            if (response.data) {
                onLogin(username, password); // Update App state if authentication is successful
                navigate('/home'); // Navigate to HomePage
            } else {
                setLoginError('Wrong username or password'); // Set error message if authentication fails
                alert(loginError);
            }
        } catch (error) {
            console.error('Login error:', error);
            alert(loginError);
            setLoginError('An error occurred during login'); // Set error message for other types of errors
        }
    };

    return (
        <div className="flex justify-center items-center h-screen">
            <div className="w-full max-w-xs flex flex-col items-center">
                <form onSubmit={handleLoginSubmit} className="w-full flex flex-col">
                    <input
                        className="px-3 py-2 mb-3 border rounded shadow appearance-none text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                        type="text"
                        placeholder="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                    <input
                        className="px-3 py-2 mb-3 border rounded shadow appearance-none text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <button
                        type="submit"
                        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline mb-4"
                    >
                        Login
                    </button>

                </form>
                <button
                    className="w-full bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                    onClick={handleRegisterClick}
                >
                    Register
                </button>
            </div>
        </div>
    );
}

export default Login;
