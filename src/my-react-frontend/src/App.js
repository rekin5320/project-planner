import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate  } from 'react-router-dom';
import { GoogleOAuthProvider } from '@react-oauth/google';
import Login from './components/Login';
import Register from './components/Register';
import HomePage from './components/HomePage';
import ProjectDetails from "./components/ProjectDetails";
import TaskDetails from "./components/TaskDetails";
function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [user, setUser] = useState();
    const [selectedProject, setSelectedProject] = useState(null);
    const [selectedTask, setSelectedTask] = useState(null);



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

    const handleSelectProject = (project) => {
        setSelectedProject(project);
    };

    const handleSelectTask = (task) => {
        setSelectedTask(task);
        //alert(selectedTask.id);
    };

    return (
        <div className="bg-custom-background">
            <Router>
                <GoogleOAuthProvider clientId="653829545632-s1tg9di96ernst657soqhvtdt37vssp8.apps.googleusercontent.com">
                    <Routes>
                        <Route path="/" element={<Navigate to="/login"/>}/>
                        <Route path="/login" element={<Login onLogin={handleLogin}/>}/>
                        <Route path="/register" element={<Register onRegister={handleRegister}/>}/>
                        <Route path="/home" element={isLoggedIn ? <HomePage user={user} onLogout={handleLogout} changeSelectedProject={handleSelectProject}  /> : <Navigate to="/login"/>}/>
                        <Route path="/project/:projectId" element={<ProjectDetails project={selectedProject} changeSelectedTask={handleSelectTask}/>}/>
                        <Route path="/task/:taskId" element={<TaskDetails task = {selectedTask} />}/>
                    </Routes>
                </GoogleOAuthProvider>
            </Router>
        </div>
    );
}

export default App;
