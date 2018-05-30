const Sequelize = require('sequelize')

/* Setup DB*/
const sequelize = new Sequelize('database', 'clearing_db', 'clearing_db', {
  host: 'localhost',
  dialect: 'sqlite',
  operatorsAliases: false,

  pool: {
    max: 5,
    min: 0,
    acquire: 30000,
    idle: 10000
  },

  // SQLite only
  storage: 'utils/db/database.sqlite'
})

/* Authentication */
const setupDB = sequelize
  .authenticate()
  .then(() => {
    console.log('Connection has been established successfully.');
  })
  .catch(err => {
    console.error('Unable to connect to the database:', err);
  })

/* Entities defined in utils/db/model/entities */

module.exports = sequelize