import React, {useState, useEffect} from "react";
import axios from "axios";

const ProjectManagementComponent = () => {
    const [projects, setProjects] = useState([]);
    const [newProjectName, setNewProjectName] = useState("");
    const [newProjectOwnerId, setNewProjectOwnerId] = useState("");

    useEffect(() => {
        axios.get("http://localhost:8080/api/projects/all")
            .then(response => {
                setProjects(response.data);
            })
            .catch(error => console.error("API call error:", error));
    }, []);

    const handleAddProject = (e) => {
        e.preventDefault();
        const newProject = {name: newProjectName, owner: {id: newProjectOwnerId}};
        axios.post("http://localhost:8080/api/projects/add", newProject)
            .then(response => {
                setProjects([...projects, response.data]);
                setNewProjectName(""); // Clear the input field
            })
            .catch(error => console.error("Error adding project:", error));
    };

    const handleDeleteProject = (projectId) => {
        axios.delete(`http://localhost:8080/api/projects/delete/${projectId}`)
            .then(() => {
                setProjects(projects.filter(project => project.id !== projectId));
            })
            .catch(error => console.error("Error deleting project:", error));
    };

    return (
        <div className="flex items-center justify-center">
            <div className="flex flex-col items-center justify-center overflow-hidden">
                {/* Scrollable container for the project list */}
                <div className="overflow-y-auto overflow-x-hidden max-h-[calc(100vh-150px)] w-full hide-scrollbar">
                    {projects.map(project => (
                        <div key={project.id} className="bg-custom-blue shadow-md rounded-md p-4 mb-2 flex items-center justify-between w-full max-w-md mr-2" >
                            <span className="text-gray-800 font-bold">ID: {project.id}</span>
                            <span className="text-gray-800 ml-2 mr-2">{project.name}</span>
                            <button
                                onClick={() => handleDeleteProject(project.id)}
                                className="bg-custom-gray text-white py-2 px-4 rounded hover:bg-pink-600"
                                >
                                Delete
                            </button>
                        </div>
                    ))}
                </div>
                {/* Form for adding a new project */}
                <form onSubmit={handleAddProject} className="mb-4">
                    <input
                        type="text"
                        value={newProjectName}
                        onChange={(e) => setNewProjectName(e.target.value)}
                        placeholder="Name"
                        className="mr-2 p-2 border border-custom-lightgray rounded bg-custom-lightgray"
                    />
                    <input
                        type="text"
                        value={newProjectOwnerId}
                        onChange={(e) => setNewProjectOwnerId(e.target.value)}
                        placeholder="Owner ID"
                        className="mr-2 p-2 border border-custom-lightgray rounded bg-custom-lightgray"
                    />
                    <button
                        type="submit"
                        className="bg-custom-gray text-white py-2 px-4 rounded hover:bg-pink-600"
                        >
                        Add Project
                    </button>
                </form>
            </div>
        </div>
    );
};

export default ProjectManagementComponent;

