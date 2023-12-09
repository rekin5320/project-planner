import "./tailwind.css";
import React from "react";
import UserManagementComponent from "./components/UserManagementComponent";
import ProjectManagementComponent from "./components/ProjectManagementComponent";

function App() {
    return (
        <div className="flex justify-center gap-x-40 bg-custom-pink min-h-screen">
            <UserManagementComponent/>
            <ProjectManagementComponent/>
        </div>
    );
}

export default App;
