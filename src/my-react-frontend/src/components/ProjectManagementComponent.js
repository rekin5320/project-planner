import React, {useState, useEffect} from "react";
import axios from "axios";

const ProjectManagementComponent = ({user, projectManagementUpdate}) => {
    const [projects, setProjects] = useState([]);
    const [newProjectName, setNewProjectName] = useState("");

    useEffect(() => {
        axios.get(`/api/users/${user.id}/projects`)
            .then(response => {
                setProjects(response.data);
            })
            .catch(error => console.error("API call error:", error));
    }, [user.id, projectManagementUpdate]); // Include projectManagementUpdate in the dependency array

    const handleAddProject = (e) => {
        e.preventDefault();

        const newProject = {name: newProjectName, owner: {id: user.id}};
        axios.post("/api/projects/add", newProject)
            .then(response => {
                const addedProject = {
                    ...response.data,
                    owner: user
                };

                setProjects([...projects, addedProject]);
                setNewProjectName(""); // Clear the input field
            })
            .catch(error => console.error("Error adding project:", error));
    };

    const handleDeleteProject = (projectId) => {
        axios.delete(`/api/projects/delete/${projectId}`)
            .then(() => {
                setProjects(projects.filter(project => project.id !== projectId));
            })
            .catch(error => console.error("Error deleting project:", error));
    };

    return (
        <div className="flex flex-col items-center justify-center overflow-hidden">
            <h2 className="text-3xl text-white mb-2">Your Projects</h2>
            {/* Scrollable container for the project list */}
            <div className="mylist-container">
                {projects.map(project => (
                    <div key={project.id} className="mylist-entry" >
                        <span className="text-gray-800">
                            <span className="font-bold">{project.name}</span>
                            <span className="ml-2">id: {project.id}</span>
                            <span className="ml-2">owner: {project.owner.name}</span>
                            <span className="ml-2">
                                <span>members: </span>
                                {project.members.map((member, index) => (
                                    <span>{member.name + (index + 1 !== project.members.length ? ', ' : '')}</span>
                                ))}
                            </span>
                        </span>
                        <button
                            onClick={() => handleDeleteProject(project.id)}
                            className="mybutton"
                            >
                            Delete
                        </button>
                    </div>
                ))}
            </div>

            <form onSubmit={handleAddProject} className="w-full">
                <div className="flex">
                    <input
                        type="text"
                        value={newProjectName}
                        onChange={(e) => setNewProjectName(e.target.value)}
                        placeholder="Name"
                        className="myinput mr-2 grow"
                    />
                    <button
                        type="submit"
                        className="mybutton"
                        >
                        Add project
                    </button>
                </div>
            </form>
        </div>
    );
};

export default ProjectManagementComponent;
