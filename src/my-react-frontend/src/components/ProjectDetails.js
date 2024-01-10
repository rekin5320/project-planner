import React, { useState, useEffect  } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const ProjectDetails = ({ project }) => {
    // Use state for the description
    const [tasks, setTasks] = useState([]);
    const [description, setDescription] = useState(project.description);
    const [newDescription, setNewDescription] = useState(project.description);
    const [newTaskTitle, setNewTaskTitle] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        // The condition is moved inside the useEffect
        alert(project.id);



        if (project && project.id) {
            axios.get(`/api/projects/${project.id}/tasks`, project.id)
                .then(response => {
                    setTasks(response.data);
                })
                .catch(error => console.error('Error fetching tasks:', error));
        }
    }, [project]);

    if (!project) {
        return <div className="text-center text-lg text-gray-600">No project selected</div>;
    }

    const handleDescriptionChange = (e) => {
        setNewDescription(e.target.value);
    };

    const handleAddTask = () => {
        const requestBody = {
            title: newTaskTitle,
            creatorId: project.owner.id,
            projectId: project.id
        };

        // Sending a POST request to the create task endpoint
        axios.post('/api/tasks/create', requestBody)
            .then(response => {
                // Assuming you want to add the new task to your existing tasks list
                setTasks([...tasks, response.data]);
                setNewTaskTitle(""); // Reset the input field after task creation
                alert('Task created successfully');
            })
            .catch(error => {
                console.error('Error creating task:', error);
                alert('Failed to create task');
            });
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
        <div className="min-h-screen  p-6 bg-custom-background ">
            <div  className = "flex justify-between" >
                {/* Left Column */}
                <div className="flex-1 max-w-xl mx-auto bg-white shadow-lg rounded-lg overflow-hidden p-4">
                    <h1 className="text-2xl font-bold text-gray-800">{project.name}</h1>
                    <h2 className="text-md text-gray-600">ID: {project.id}</h2>
                    <p className="text-md text-gray-700">Description: {description}</p>

                    <div className="flex mt-2">
                        <input
                            type="text"
                            value={newDescription}
                            onChange={handleDescriptionChange}
                            className="bg-gray-200 p-2 rounded w-full mt-2"
                        />
                        <button
                            onClick={updateProjectDescription}
                            className="mt-2 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded w-full"
                        >
                            Change Description
                        </button>
                    </div>
                </div>

                {/* Right Column */}
                <div className="flex-1 max-w-xl mx-auto bg-white shadow-lg rounded-lg overflow-hidden p-4 ml-4">
                    <h2 className="text-xl font-bold text-gray-800">Tasks</h2>
                    <div className="tasks-container">
                        {tasks.length > 0 ? (
                            tasks.map(task => (
                                <div key={task.id} className="bg-white shadow p-4 rounded mb-2">
                                    <h3 className="text-lg font-bold">{task.title}</h3>
                                    <p>{task.description}</p>
                                </div>
                            ))
                        ) : (
                            <p className="text-gray-600">No tasks found for this project.</p>
                        )}
                    </div>

                    <div className="flex mt-2">
                        <input
                            type="text"
                            value={newTaskTitle}
                            onChange={(e) => setNewTaskTitle(e.target.value)}
                            placeholder="New Task Title"
                            className="bg-gray-200 p-2 rounded w-full mt-2"
                        />
                        <button
                            onClick={handleAddTask}
                            className="mt-2 bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded w-full"
                        >
                            Add Task
                        </button>
                    </div>
                </div>


            </div>
            <div className="flex justify-center mt-4">
                <button onClick={goBack} className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                    Go Back to Home
                </button>
            </div>
        </div>
    );
};

export default ProjectDetails;