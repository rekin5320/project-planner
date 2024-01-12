import React, {useState} from 'react';
import axios from "axios";

function TaskDetails(task) {

    const [task2, setTask2] = useState(task);
    const [description, setDescription] = useState(task.description);
    const [newDescription, setNewDescription] = useState(task.description);


    if (!task2) {
        return <div className="text-center text-lg text-gray-600">No task selected</div>;
    }
    const handleDescriptionChange = (e) => {
        setNewDescription(e.target.value);
    };

    const updateTaskDescription = () => {
        const updatedTask = { ...task2, description: newDescription };
        alert(task2.id);
        alert(`/api/tasks/update/${task2.id}`)
        axios.put(`/api/tasks/update/${task2.id}`, updatedTask)
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
        <div className="flex-1 max-w-xl mx-auto bg-white shadow-lg rounded-lg overflow-hidden p-4 ml-4">
            <h1 className="text-4xl font-bold">{task2.title}</h1>
            <p className="text-md"><span className="font-bold">Description:</span> {task2.description}</p>

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
    );
}

export default TaskDetails;