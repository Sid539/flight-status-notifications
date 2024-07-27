import React, { useState, useEffect } from 'react';
import axios from 'axios';

function Notifications() {
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    axios.get('/api/notifications')
      .then(response => setNotifications(response.data))
      .catch(error => console.error('Error fetching notifications:', error));
  }, []);

  return (
    <div>
      <h2>Notifications</h2>
      <ul>
        {notifications.map(notification => (
          <li key={notification.id}>
            {notification.message}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Notifications;
