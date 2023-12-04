import React, { useState, useEffect } from 'react';
import axios from 'axios';

const UserComponent = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/users/all')
      .then(response => {
        console.log('API response:', response.data);
        setUsers(response.data);
      })
      .catch(error => {
        console.log('API call error:', error);
      });
  }, []);

  return (
    <div className="flex flex-col items-center justify-center">
      <ul className="list-none">
        {users.map(user => (
          <li key={user.id} className="bg-pink-200 border border-pink-300 rounded-md p-4 mb-2 shadow-md hover:bg-pink-300">
            <div>ID: {user.id}</div>
            <div>Name: {user.name}</div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default UserComponent;