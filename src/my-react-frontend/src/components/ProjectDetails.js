import React, { useState, useEffect  } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const ProjectDetails = ({project, changeSelectedTask, updateTasks, updateMembers}) => {
    const [project2, setProject2] = useState(project);
    const [members, setMembers] = useState([]);
    const [tasks, setTasks] = useState([]);
    const [newMember, setNewMember] = useState([]);
    const [description, setDescription] = useState(project.description);
    const [newDescription, setNewDescription] = useState(project.description);
    const [newTaskTitle, setNewTaskTitle] = useState("");
    const [currentMembersPage, setCurrentMembersPage] = useState(0);
    const [totalMembersPages, setMembersTotalPages] = useState(0);
    const [currentTasksPage, setCurrentTasksPage] = useState(0);
    const [totalTasksPages, setTasksTotalPages] = useState(0);
    const pageSize = 5;
    const navigate = useNavigate();

    useEffect(() => {
        if (project2 && project2.id) {
            doUpdateMembers();
            doUpdateTasks();
        }
    }, [project2, changeSelectedTask, currentTasksPage, currentMembersPage]);

    if (!project2) {
        return <div className="text-center text-lg text-gray-600">No project selected</div>;
    }

    const doUpdateMembers = () => {
        axios.get(`/api/projects/${project.id}/members`, {params: {page: currentMembersPage, size: pageSize}})
            .then(response => {
                setMembers(response.data.content);
                setMembersTotalPages(response.data.totalPages);
            })
            .catch(error => console.error("Error fetching members:", error));
    }

    const doUpdateTasks = () => {
        axios.get(`/api/projects/${project.id}/tasks`, {params: {page: currentTasksPage, size: pageSize}})
            .then(response => {
                setTasks(response.data.content);
                setTasksTotalPages(response.data.totalPages);
            })
            .catch(error => console.error("Error fetching tasks:", error));
    }

    const handleMembersPageChange = (newPage) => {
        setCurrentMembersPage(newPage);
    }

    const handleTasksPageChange = (newPage) => {
        setCurrentTasksPage(newPage);
    };

    const handleSelectTask = (task) => {
        changeSelectedTask(task);
        navigate(`/task/${task.id}`);
    };

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
                setNewTaskTitle("");
                doUpdateTasks();
                alert("Task created successfully");
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
                setDescription(newDescription);
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
                setMembers([...members, response.data]);
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
                alert("Task deleted successfully");
            })
            .catch(error => {
                console.error('Error creating task:', error);
                alert('Failed to delete task');
            });

    }

    const deleteMember = (name) =>{
        const requestBody = {
            name: name
        };

        axios.post(`/api/projects/removeUser/${project.id}`, requestBody)
            .then(response => {
                const updatedMembers = project2.members.filter(item => item.name !== name);
                setProject2(prevProject => ({
                    ...prevProject,
                    members: updatedMembers
                }));
                alert('User deleted successfully');
            })
            .catch(error => {
                console.error('Error assigning user:', error);
                alert('Failed to assign user');
            });

    }


    const handleTaskIsDone = (task) => {
        // Toggle the isDone property of the task
        //alert(task.isDone);

        const updatedTask = { ...task, done: !task.done };
        alert(updatedTask.done);

        axios.put(`/api/tasks/update/${task.id}`, updatedTask)
            .then(response => {
                alert('Success');
                doUpdateTasks();
            })
            .catch(error => {
                console.error('Error updating task:', error);
                alert('Failed to update task status');
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
                    <h2 className="text-2xl font-bold">Members</h2>
                    <ul className="list-container">
                        {members && members.length > 0 ? (
                            members.map(member => (
                                <div key={member.id} className="bg-white border-solid border-2 rounded-[6px] p-4 mb-2 flex items-center">
                                    <h3 className="text-lg font-bold flex-1">{member.name}</h3>
                                    <button
                                        onClick={() => deleteMember(member.name)} // Assuming deleteTask function needs task's id
                                        className="mybutton-red"
                                    >
                                        Delete
                                    </button>
                                </div>

                            ))
                        ) : (
                            <p>No members in this project.</p>
                        )}
                    </ul>

                    <div className="pagination-controls flex justify-center items-center mt-2">
                        {[...Array(totalMembersPages).keys()].map(page => (
                            <button
                                key={page}
                                onClick={() => handleMembersPageChange(page)}
                                className={`mybutton-pagination ${page === currentMembersPage ? "" : "mybutton-pagination-other"}`}
                            >
                                {page + 1}
                            </button>
                        ))}
                    </div>

                    <div className="flex mt-4">
                        <input
                            type="text"
                            value={newMember}
                            onChange={(e) => setNewMember(e.target.value)}
                            placeholder="Enter User name"
                            className="bg-gray-200 p-2 rounded w-full"
                        />
                        <button
                            onClick={handleAssignUser}
                            className="mybutton-green px-1 w-40"
                        >
                            Assign User
                        </button>
                    </div>

                </div>

                {/* Middle Column */}
                <div className="flex-1 max-w-xl mx-auto bg-white shadow-lg rounded-lg overflow-hidden p-4 ml-4">
                    <h1 className="text-4xl font-bold">{project2.name}</h1>
                    <p className="text-md"><span className="font-bold">Description:</span> {description}</p>

                    <div className="flex mt-2">
                        <input
                            type="text"
                            value={newDescription}
                            onChange={handleDescriptionChange}
                            className="bg-gray-200 p-2 rounded w-full"
                        />
                        <button
                            onClick={updateProjectDescription}
                            className="mybutton px-1 w-64"
                        >
                            Change description
                        </button>
                    </div>
                </div>

                {/* Right Column */}
                <div className="flex-1 max-w-xl mx-auto bg-white shadow-lg rounded-lg overflow-hidden p-4 ml-4">
                    <h2 className="text-2xl font-bold">Tasks</h2>
                    <div className="list-container">
                        {tasks.length > 0 ? (
                            tasks.map(task => (
                                <div key={task.id} className="bg-white border-solid border-2 rounded-[6px] p-4 mb-2 flex items-center" onClick={() => handleSelectTask(task)}>

                                    <div className="form-checkbox mr-2">
                                        <input
                                            type="checkbox"
                                            checked={task.done}
                                            onChange={() => handleTaskIsDone(task)}
                                            className="checked:bg-blue-500 checked:border-transparent focus:outline-none focus:ring-2 focus:ring-blue-300 h-5 w-5 text-blue-600 border-gray-300 rounded"
                                        />
                                    </div>

                                    <div className="flex-1">
                                        <h3 className="text-lg font-bold">{task.title}</h3>
                                        <p>{task.description}</p>
                                    </div>
                                    <button
                                        onClick={() => deleteTask(task.id)}
                                        className="mybutton-red"
                                    >
                                        Delete
                                    </button>
                                </div>
                            ))
                        ) : (
                            <p className="text-gray-600">No tasks found for this project.</p>
                        )}
                    </div>

                    <div className="pagination-controls flex justify-center items-center mt-2">
                        {[...Array(totalTasksPages).keys()].map(page => (
                            <button
                                key={page}
                                onClick={() => handleTasksPageChange(page)}
                                className={`mybutton-pagination ${page === currentTasksPage ? "" : "mybutton-pagination-other"}`}
                            >
                                {page + 1}
                            </button>
                        ))}
                    </div>

                    <div className="flex mt-4">
                        <input
                            type="text"
                            value={newTaskTitle}
                            onChange={(e) => setNewTaskTitle(e.target.value)}
                            placeholder="New Task title"
                            className="bg-gray-200 p-2 rounded w-full"
                        />
                        <button
                            onClick={handleAddTask}
                            className="mybutton-green px-1 w-32"
                        >
                            Add Task
                        </button>
                    </div>
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
