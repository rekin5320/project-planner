import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const ProjectDetails = ({ project }) => {
    // Use state for the description
    const [description, setDescription] = useState(project.description);
    const [newDescription, setNewDescription] = useState(project.description);
    const navigate = useNavigate();

    if (!project) {
        return <div className="text-center text-lg text-gray-600">No project selected</div>;
    }

    const handleDescriptionChange = (e) => {
        setNewDescription(e.target.value);
    };

    const updateProjectDescription = () => {
        const updatedProject = { ...project, description: newDescription };

        axios.put(`/api/projects/update/${project.id}`, updatedProject)
            .then(response => {
                setDescription(newDescription); // Update state to reflect new description
                alert('Project description updated successfully');
            })
            .catch(error => {
                console.error('Error updating project:', error);
                alert('Failed to update project description');
            });
    };

    const goBack = () => {
        navigate('/home');
    };

    return (
        <div className="min-h-screen bg-gray-100 p-6 bg-custom-background">
            <div className="max-w-4xl mx-auto bg-white shadow-lg rounded-lg overflow-hidden">
                <div className="p-4">
                    <h1 className="text-2xl font-bold text-gray-800">{project.name}</h1>
                    <h2 className="text-md text-gray-600">ID: {project.id}</h2>
                    <p className="text-md text-gray-700">Description: {description}</p>
                    <input
                        type="text"
                        value={newDescription}
                        onChange={handleDescriptionChange}
                        className="bg-gray-200 p-2 rounded"
                    />
                    <button
                        onClick={updateProjectDescription}
                        className="ml-2 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                    >
                        Change description
                    </button>
                </div>
            </div>

            <div className="flex justify-center mt-4">
                <button onClick={goBack} className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                    Go back to Home
                </button>
            </div>
        </div>
    );
};

export default ProjectDetails;
