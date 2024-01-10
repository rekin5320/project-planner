import React from 'react';

const ProjectDetails = ({ project }) => {
    if (!project) {
        return <div className="text-center text-lg text-gray-600">No project selected</div>;
    }

    return (
        <div className="min-h-screen bg-gray-100 p-6 bg-custom-background" >
            <div className="max-w-4xl mx-auto bg-white shadow-lg rounded-lg overflow-hidden">
                <div className="p-4">
                    <h1 className="text-2xl font-bold text-gray-800">Project: {project.name}</h1>
                    <h2 className="text-md text-gray-600">ID: {project.id}</h2>
                    {/* Add more project details here as needed */}
                </div>

                <div className="p-4 border-t border-gray-200">
                    {/* Add more detailed information about the project here */}
                    <p className="text-md text-gray-700">Description: {project.description}</p>
                    <p className="text-md text-gray-700">Owner: {project.owner && project.owner.name}</p>
                    {/* Add more fields as necessary */}
                </div>
            </div>
        </div>
    );
};

export default ProjectDetails;