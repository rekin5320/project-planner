import React, {useState, useEffect} from "react";
import axios from "axios";

const ProjectManagementComponent = ({user, updateProjects, projectManagementUpdate, onSelectProject}) => {
    const [projects, setProjects] = useState([]);
    const [newProjectName, setNewProjectName] = useState("");
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const pageSize = 5;

    useEffect(() => {
        axios.get(`/api/users/${user.id}/projects`, {params: {page: currentPage, size: pageSize}})
            .then(response => {
                setProjects(response.data.content);
                setTotalPages(response.data.totalPages);
            })
            .catch(error => console.error("API call error:", error));
    }, [user.id, currentPage, projectManagementUpdate]);

    const handlePageChange = (newPage) => {
        setCurrentPage(newPage);
    };

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
                updateProjects(user);
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


            <div className="mylist-container">
                {projects.map(project => (
                    <div key={project.id} className="mylist-entry">
                        <span
                            className="text-gray-800 font-bold cursor-pointer"
                            onClick={() => onSelectProject(project)}
                        >
                            {project.name}
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


            {/* Scrollable container for the project list */}
            <div className="pagination-controls flex justify-center items-center mt-2">
                {[...Array(totalPages).keys()].map(page => (
                    <button
                        key={page}
                        onClick={() => handlePageChange(page)}
                        className={`mybutton-pagination ${page === currentPage ? "" : "mybutton-pagination-other"}`}
                    >
                        {page + 1}
                    </button>
                ))}
            </div>

            <form onSubmit={handleAddProject} className="w-full mt-4">
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
