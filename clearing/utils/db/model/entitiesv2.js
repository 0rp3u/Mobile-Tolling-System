const Sequelize = require('sequelize')

module.exports = function(db){

    /* User */
    const User = db.define('User', {
        nif:        { type: Sequelize.INTEGER, primaryKey: true },
        name:       { type: Sequelize.STRING },
        password:   { type: Sequelize.STRING },
        state:      { type: Sequelize.ENUM, values: ['GOOD', 'BAD'] },
        contact:    { type: Sequelize.STRING }
    }, {
        freezeTableName: true
    })

    User.outputModel = function(user){
        const output = {
            nif: user.nif,
            name: user.name,
            password: user.password,
            state: user.state,
            contact: user.contact
        }
        return output
    }

    /* Vehicle */
    const Vehicle = db.define('Vehicle', {
        id:         { type: Sequelize.INTEGER, primaryKey: true, autoIncrement: 1},
        plate:      { type: Sequelize.STRING, unique: true },
        nif:        { type: Sequelize.INTEGER},//, references: { model: User, Key: 'nif' } },
        category:   { type: Sequelize.STRING },
        state:      { type: Sequelize.ENUM, values: ['PENDING', 'CONFIRMED'] }
    }, {
        freezeTableName: true
    })

    Vehicle.belongsTo(User, {
        foreignKey: 'nif',
        constraints: false,
        as: 'FK_nif'
    })

    /* Trip */
    const Trip = db.define('Trip', {
        id:             { type: Sequelize.INTEGER, primaryKey: true, autoIncrement: 1 },
        state:          { type: Sequelize.ENUM, values: ['STARTED', 'PENDING', 'CONFIRMED'] },
        vehicle_used:   { type: Sequelize.INTEGER },//, references: { model: Vehicle, Key: 'id' } },
    }, {
        freezeTableName: true
    })
    
    Trip.belongsTo(Vehicle, {
        foreignKey: 'vehicle_used',
        constraints: false,
        as: 'FK_vehicle'
    })

    /* Toll */
    const Toll = db.define('Toll', {
        id:                     { type: Sequelize.INTEGER, primaryKey: true, autoIncrement: 1 },
        name:                   { type: Sequelize.STRING, unique: true},
        toll_type:              { type: Sequelize.ENUM, values: ['ONE-WAY', 'NORMAL'] },
        geolocation_latitude:   { type: Sequelize.FLOAT },
        geolocation_longitude:  { type: Sequelize.FLOAT },
        azimuth:                { type: Sequelize.FLOAT },
        region:                 { type: Sequelize.STRING },
        road:                   { type: Sequelize.STRING }
    }, {
        freezeTableName: true
    })
    

    /* Transaction */
    const Transaction = db.define('Transaction', {
        id:         { type: Sequelize.INTEGER, primaryKey: true, autoIncrement: 1 },
        direction:  { type: Sequelize.STRING },
        etc_id:     { type: Sequelize.STRING },
        timestamp:  { type: Sequelize.TIME },
        issuer:     { type: Sequelize.STRING }
    }, {
        freezeTableName: true
    })

    Transaction.Trip = Toll.belongsToMany(Trip, { through: Transaction, as: 'trip_id' })
    Transaction.Toll = Trip.belongsToMany(Toll, { through: Transaction, as: 'toll_id'})
    
    return {
        User,
        Vehicle,
        Trip,
        Toll,
        Transaction
    }
}