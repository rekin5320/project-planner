import "./tailwind.css";
import React from "react";
import UserManagementComponent from "./components/UserManagementComponent";
import ProjectManagementComponent from "./components/ProjectManagementComponent";

function App() {
    return (
        <div className="flex flex-wrap justify-evenly content-evenly min-h-screen bg-custom-background">
            <UserManagementComponent/>
            <ProjectManagementComponent/>
        </div>
    );
}

export default App;
