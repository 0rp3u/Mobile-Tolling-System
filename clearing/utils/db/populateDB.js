module.exports = async function populateDB(){
    var {User, Vehicle, Trip, Toll, Transaction} = require('./model/entities')
    console.log('starting populating DB')

    await Promise.all([
        User.sync({force: true}),
        Vehicle.sync({force: true}),
        Trip.sync({force: true}),
        Toll.sync({force: true}),
    ])

    await Transaction.sync({force: true})

    await Promise.all([
        /* Users */
        User.create({
            nif: 123,
            name: 'Joao',
            password: 'pass',
            state: 'GOOD'
        }),

        User.create({
            nif: 1234,
            name: 'David',
            password: 'pass',
            state: 'GOOD'
        }),

        /* Vehicles */
        Vehicle.create({
            id: 1,
            plate: 'AA-00-01',
            owner: 123,
            category: 1
        }),

        Vehicle.create({
            id: 2,
            plate: 'AA-00-05',
            owner: 123,
            category: 5
        }),

        Vehicle.create({
            id: 3,
            plate: 'BB-00-01',
            owner: 1234,
            category: 1
        }),

        Vehicle.create({
            id: 4,
            plate: 'BB-00-02',
            owner: 1234,
            category: 2
        }),

        /* Trips */
        Trip.create({
            state: 'CONFIRMED',
            vehicle_used: 1
        }),

        /* Tolls */
        Toll.create({
            id: 1,
            name: 'Portagem de Carcavelos',
            toll_type: 'NORMAL',
            geolocation_latitude: 38.7054309,
            geolocation_longitude: -9.3407711
        })
    ])
    console.log('-----------------------------------------------------Promise.all finished')
    //.then( values => { console.log('Finished Promise.all') } )
    //.catch( reason => console.log(reason) )

    /* Transactions */
    Transaction.create({
        id: 1,
        direction: 'NORTH-SOUTH',
        etc_id: 'etc id 1'
    })
}