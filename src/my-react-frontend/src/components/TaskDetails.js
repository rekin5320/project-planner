import React from 'react';

function TaskDetails(task) {

    return (
        <div>
            <h1>Task Details</h1>
            <p><strong>Task ID:</strong> {task.id}</p>
            <p><strong>Task Title:</strong> {task.title}</p>
        </div>
    );
}

export default TaskDetails;