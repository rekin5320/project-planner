import React from 'react';
import ProjectManagementComponent from "./ProjectManagementComponent";

function HomePage({ userCredentials }) {
    return (
        <div>
            <ProjectManagementComponent> </ProjectManagementComponent>
            <p>Username: {userCredentials.username}</p>
            <p>Password: {userCredentials.password}</p> {/* Be cautious about displaying passwords */}
        </div>
    );
}

export default HomePage;