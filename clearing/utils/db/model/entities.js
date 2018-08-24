const Sequelize = require('sequelize')
const db = require('../setup')

const User = db.define('User', {
    nif:        { type: Sequelize.INTEGER, primaryKey: true },
    name:       { type: Sequelize.STRING },
    password:   { type: Sequelize.STRING },
    state:      { type: Sequelize.ENUM, values: ['GOOD', 'BAD'] }
})

const Vehicle = db.define('Vehicle', {
    id:         { type: Sequelize.INTEGER, primaryKey: true, autoIncrement: true },
    plate:      { type: Sequelize.STRING, unique: true },
    owner:      { type: Sequelize.INTEGER, references: { model: User, Key: 'nif' } },
    category:   { type: Sequelize.STRING }
})

const Transaction = db.define('Transaction', {
    id:             { type: Sequelize.INTEGER, primaryKey: true, autoIncrement: true },
    state:          { type: Sequelize.ENUM, values: ['STARTED', 'PENDING', 'CONFIRMED'] },
    vehicle_used:   { type: Sequelize.INTEGER, references: { model: Vehicle, Key: 'id' } },
})

const Toll = db.define('Toll', {
    id:                     { type: Sequelize.INTEGER, primaryKey: true, autoIncrement: true },
    name:                   { type: Sequelize.STRING },
    toll_type:              { type: Sequelize.ENUM, values: ['ONE-WAY', 'NORMAL'] },
    geolocation_latitude:   { type: Sequelize.FLOAT },
    geolocation_longitude:  { type: Sequelize.FLOAT },
    azimuth:                { type: Sequelize.FLOAT },
    region:                 { type: Sequelize.STRING },
    road:                   { type: Sequelize.STRING }
})

const Transaction = db.define('Transaction', {
    id:         { type: Sequelize.INTEGER, primaryKey: true, autoIncrement: true },
    direction:  { type: Sequelize.STRING },
    etc_id:     { type: Sequelize.STRING },
    toll_id:    { type: Sequelize.INTEGER, references: { model: Toll, Key: 'id' } },
    Transaction_id:    { type: Sequelize.INTEGER, references: { model: Transaction, Key: 'id' } }
})
Toll.belongsToMany(Transaction, { through: Transaction })
Transaction.belongsToMany(Toll, { through: Transaction })

module.exports = {
    User,
    Vehicle,
    Transaction,
    Toll,
    Transaction
}