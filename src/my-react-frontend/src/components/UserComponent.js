import React, { useState, useEffect } from 'react';
import axios from 'axios';

const UserComponent = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/users/all')
      .then(response => {
        setUsers(response.data);
      })
      .catch(error => {
        console.error('API call error:', error);
      });
  }, []);

  const deleteUser = (userId) => {
    axios.delete(`http://localhost:8080/api/users/delete/${userId}`)
      .then(() => {
        // Update the UI by filtering out the deleted user
        setUsers(users.filter(user => user.id !== userId));
      })
      .catch(error => {
        console.error('Error deleting user:', error);
      });
  };

  return (
    <div className="flex flex-col items-center justify-center">
      {users.map(user => (
        <div key={user.id} className="bg-white shadow-md rounded-md p-4 mb-2 flex items-center justify-between w-full max-w-md">
          <div>
            <span className="text-gray-800 font-bold">ID: {user.id}</span> {/* Display user ID */}
            <span className="text-gray-800 ml-4">{user.name}</span> {/* Display user name */}
          </div>
          <button
            onClick={() => deleteUser(user.id)}
            className="bg-pink-500 text-white py-2 px-4 rounded hover:bg-pink-600 focus:outline-none focus:ring-2 focus:ring-pink-300 focus:ring-opacity-50"
          >
            Delete
          </button>
        </div>
      ))}
    </div>
  );
};

export default UserComponent;