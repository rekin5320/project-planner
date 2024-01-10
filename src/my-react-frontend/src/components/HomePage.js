import React, {useState} from "react";
import ProjectManagementComponent from "./ProjectManagementComponent";
import { useNavigate } from "react-router-dom";
import { googleLogout } from '@react-oauth/google';
import './Style/HomePage.css';
import default_profile_pic from "./Images/npc_face.jpg";
import UserEditComponent from "./UserEditComponent";

function HomePage({ user, onLogout, changeSelected }) {

    const [currentUser, setCurrentUser] = useState(user);
    const [projectManagementUpdate, setProjectManagementUpdate] = useState(0);

    const navigate = useNavigate();

    const handleSelectProject = (project) => {
        changeSelected(project);
        navigate(`/project/${project.id}`);
    };
    const handleLogoutClick = () => {
        onLogout();
        googleLogout();
        navigate("/login");
        alert("User logged out");
    }

    const handleUserUpdate = (updatedUser) => {
        setCurrentUser(updatedUser);

        // Increment the state variable to trigger ProjectManagementComponent update
        setProjectManagementUpdate(prevValue => prevValue + 1);
    };

    const updateProjects = (user) => {
        // Increment the state variable to trigger ProjectManagementComponent update
        setProjectManagementUpdate(prevValue => prevValue + 1);
    }

    return (
        <div className="min-h-screen bg-custom-background">
            <div className="user-section">
                <img src={(currentUser.google && currentUser.google.picture) || default_profile_pic} alt="User avatar" className="profile-pic rounded-lg"/>
                <button
                    className="mybutton"
                    onClick={(e) => handleLogoutClick(e)}
                >
                    Sign out
                </button>
            </div>

            <div className="wrapper mb-3">
                <div className="text-white">
                    <h1>Welcome {currentUser.name}</h1>
                    <h2>{currentUser.email}</h2>
                </div>

                <div className="flex flex-wrap justify-evenly content-evenly">
                    <ProjectManagementComponent user={currentUser} updateProjects={updateProjects} projectManagementUpdate={projectManagementUpdate} onSelect ={handleSelectProject}/>
                </div>
            </div>

            <div className="wrapper">
                <UserEditComponent user={currentUser} onUpdateUser={handleUserUpdate} />
            </div>
        </div>
    );
}

export default HomePage;
