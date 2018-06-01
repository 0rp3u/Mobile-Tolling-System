const Sequelize = require('sequelize')

/* Setup DB*/
const DB_NAME = process.env.NODEENV || "database"
const sequelize = new Sequelize(DB_NAME, 'clearing_db', 'clearing_db', {
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
  storage: `utils/db/${DB_NAME}.sqlite`
})

/* Authentication */
sequelize
  .authenticate()
  .then(() => {
    console.log('Connection has been established successfully.')
    if(!process.env.NODEENV){
      console.log('Populating DB.')
      sequelize.sync()
        .then( () => {require('./populateDB')()})
    }
  })
  .catch(err => {
    console.error('Unable to connect to the database:', err)
  })

/* Entities defined in utils/db/model/entities */

module.exports = sequelize