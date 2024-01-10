import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { GoogleOAuthProvider } from '@react-oauth/google'; // Import the GoogleOAuthProvider
import Login from './components/Login';
import Register from './components/Register';
import HomePage from './components/HomePage';

function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [user, setUser] = useState();

    const handleLogin = (user) => {
        setUser(user);
        setIsLoggedIn(true);
    };

    const handleRegister = (user) => {
        setUser(user);
        setIsLoggedIn(true);
    };

    const handleLogout = () => {
        setUser(null);
        setIsLoggedIn(false);
    };

    return (
        <div className="bg-custom-background">
            <Router>
                <GoogleOAuthProvider clientId="653829545632-s1tg9di96ernst657soqhvtdt37vssp8.apps.googleusercontent.com">
                    <Routes>
                        <Route path="/" element={<Navigate to="/login"/>}/>
                        <Route path="/login" element={<Login onLogin={handleLogin}/>}/>
                        <Route path="/register" element={<Register onRegister={handleRegister}/>}/>
                        <Route path="/home" element={isLoggedIn ? <HomePage user={user} onLogout={handleLogout}/> : <Navigate to="/login"/>}/>
                    </Routes>
                </GoogleOAuthProvider>
            </Router>
        </div>
    );
}

export default App;
