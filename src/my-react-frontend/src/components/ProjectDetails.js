import React, { useState, useEffect  } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { FaCalendarAlt } from "react-icons/fa";

const ProjectDetails = ({ project }) => {
    // Use state for the description
    const [tasks, setTasks] = useState([]);
    const [newMember, setNewMember] = useState([]);
    const [description, setDescription] = useState(project.description);
    const [newDescription, setNewDescription] = useState(project.description);
    const [newTaskTitle, setNewTaskTitle] = useState("");
    const navigate = useNavigate();

    const handleAddEventClick = () => {
        window.open('https://calendar.google.com', '_blank');
    };

    useEffect(() => {
        // The condition is moved inside the useEffect

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

    const handleAssignUser = () => {
        const requestBody = {
            name: newMember
        };

        axios.post(`/api/projects/assignUser/${project.id}`, requestBody)
            .then(response => {
                project.members = [...project.members, response.data];
                setNewMember("");
                alert('User assigned successfully');
            })
            .catch(error => {
                console.error('Error assigning user:', error);
                alert('Failed to assign user');
            });
    };

    const deleteTask = (id) =>{

        axios.delete(`/api/tasks/delete/${id}`)
            .then(response => {
                const updatedTasks = tasks.filter(task => task.id !== id);
                setTasks(updatedTasks);
                // Assuming you want to add the new task to your existing tasks list
                alert('Task created successfully');
            })
            .catch(error => {
                console.error('Error creating task:', error);
                alert('Failed to delete task');
            });

    }


    const goBack = () => {
        navigate('/home');
    };

    return (
        <div className="min-h-screen  p-6 bg-custom-background ">
            <div  className = "flex justify-between" >
                {/* Left Column */}


                <div className="flex-1 max-w-xl mx-auto bg-white shadow-lg rounded-lg overflow-hidden p-4">
                    <h2 className="text-xl font-bold text-gray-800">Members</h2>
                    <ul className="tasks-container">
                        {project.members && project.members.length > 0 ? (
                            project.members.map(member => (
                                <div key={member.id} className="bg-white shadow p-4 rounded mb-2">
                                    <h3 className="text-lg font-bold">{member.name}</h3>
                                </div>
                            ))
                        ) : (
                            <p>No members in this project.</p>
                        )}
                    </ul>
                    <div className="flex mt-2">
                        <input
                            type="text"
                            value={newMember}
                            onChange={(e) => setNewMember(e.target.value)}
                            placeholder="Enter User name"
                            className="bg-gray-200 p-2 rounded w-full"
                        />
                        <button
                            onClick={handleAssignUser}
                            className="mybutton-green px-1 w-full"
                        >
                            Assign User
                        </button>
                    </div>

                </div>

                {/* Middle Column */}

                <div className="flex-1 max-w-xl mx-auto bg-white shadow-lg rounded-lg overflow-hidden p-4 ml-4">
                    <h1 className="text-3xl font-bold text-gray-800">{project.name}</h1>
                    <p className="text-md text-gray-700"><span className="font-bold">Description:</span> {description}</p>

                    <div className="flex mt-2">
                        <input
                            type="text"
                            value={newDescription}
                            onChange={handleDescriptionChange}
                            className="bg-gray-200 p-2 rounded w-full"
                        />
                        <button
                            onClick={updateProjectDescription}
                            className="mybutton px-1 w-full"
                        >
                            Change description
                        </button>
                    </div>
                </div>

                {/* Right Column */}
                <div className="flex-1 max-w-xl mx-auto bg-white shadow-lg rounded-lg overflow-hidden p-4 ml-4">
                    <h2 className="text-xl font-bold text-gray-800">Tasks</h2>
                    <div className="tasks-container">
                        {tasks.length > 0 ? (
                            tasks.map(task => (
                                <div key={task.id} className="bg-white shadow p-4 rounded mb-2 flex items-center">
                                    <div className="flex-1">
                                        <h3 className="text-lg font-bold">{task.title}</h3>
                                        <p>{task.description}</p>
                                    </div>
                                    <button
                                        onClick={() => deleteTask(task.id)} // Assuming deleteTask function needs task's id
                                        className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded w-1/3"
                                    >
                                        Delete
                                    </button>
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
                            placeholder="New Task title"
                            className="bg-gray-200 p-2 rounded w-full"
                        />
                        <button
                            onClick={handleAddTask}
                            className="mybutton-green px-1 w-full"
                        >
                            Add Task
                        </button>
                    </div>
                    <button
                        onClick={handleAddEventClick}
                        className="mt-2 bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded w-full"
                    >
                        Add event into Google Calendar
                        <FaCalendarAlt className="icon_event"/>
                    </button>
                </div>


            </div>
            <div className="flex justify-center mt-4">
                <button onClick={goBack} className="mybutton">
                    Go back to Home
                </button>
            </div>
        </div>
    );
};

export default ProjectDetails;
