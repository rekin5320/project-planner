import React, {useState, useEffect} from 'react';
import axios from "axios";
import {useParams} from 'react-router-dom';


function TaskDetails() {
    const {taskId} = useParams();
    const [task, setTask] = useState(null);
    const [description, setDescription] = useState();
    const [newDescription, setNewDescription] = useState();
    const [title, setTitle] = useState();

    useEffect(() => {
        axios.get(`/api/tasks/${taskId}`)
            .then(response => {
                setTask(response.data);
                setDescription(response.data.description);
                setNewDescription(response.data.description);
                setTitle(response.data.title);
            })
            .catch(error => console.error('Error fetching task:', error));
    }, [taskId]);

    const handleDescriptionChange = (e) => {
        setNewDescription(e.target.value);
    };

    const updateTaskDescription = () => {
        const updatedTask = {...task, description: newDescription};
        axios.put(`/api/tasks/update/${task.id}`, updatedTask)
            .then(response => {
                setDescription(newDescription);
                alert('Project description updated successfully');
            })
            .catch(error => {
                console.error('Error updating project:', error);
                alert('Failed to update project description');
            });
    };

    return (
        <div className="min-h-screen bg-custom-background">
            <div className="flex-1 max-w-xl bg-white shadow-lg rounded-lg overflow-hidden p-4 ml-4">
                <h1 className="text-4xl font-bold">{title}</h1>
                <p className="text-md"><span className="font-bold">Description:</span> {description}</p>

                <div className="flex mt-2">
                    <input
                        type="text"
                        value={newDescription}
                        onChange={handleDescriptionChange}
                        className="bg-gray-200 p-2 rounded w-full"
                    />
                    <button
                        onClick={updateTaskDescription}
                        className="mybutton px-1 w-64"
                    >
                        Change description
                    </button>
                </div>
            </div>
        </div>
    );
}

export default TaskDetails;
