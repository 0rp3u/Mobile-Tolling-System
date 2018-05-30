const Sequelize = require('sequelize')
const db = require('../setup')

const User = db.define('User', {
    nif:        { type: Sequelize.INTEGER, primaryKey: true },
    name:       { type: Sequelize.STRING },
    password:   { type: Sequelize.STRING },
    state:      { type: Sequelize.ENUM, values: ['GOOD', 'BAD'] }
})

const Vehicle = db.define('Vehicle', {
    plate:      { type: Sequelize.STRING, primaryKey: true },
    owner:      { type: Sequelize.INTEGER, references: { model: User, Key: 'nif' } },
    category:   { type: Sequelize.STRING }
})

const Trip = db.define('Trip', {
    id:             { type: Sequelize.INTEGER, primaryKey: true },
    state:          { type: Sequelize.INTEGER },
    vehicle_used:   { type: Sequelize.STRING, references: { model: Vehicle, Key: 'plate' } },
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

module.exports = {
    User,
    Vehicle,
    Trip,
    Toll
}
/*
module.exports = Vehicle
module.exports = Trip
module.exports = Toll
*/