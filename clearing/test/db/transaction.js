var expect = require('chai').expect
const Sequelize = require('sequelize')
const setup = require('../../utils/db/setup')

const ENTITY = 'Transaction'

describe('DB' + ENTITY + 'Tests', function () {
  it('insert new ' + ENTITY, async function () {

    var sequelize = await setup()
    const t = await sequelize.transaction({
        //isolationLevel: Sequelize.Transaction.ISOLATION_LEVELS.SERIALIZABLE
    })

    var {Toll, User, Vehicle, Trip, Transaction} = sequelize.entities
    //await sequelize.sync()
    // 1. ARRANGE
    try{
        const toll_mock = {
          toll_type: 'NORMAL',
          geolocation_latitude: 38.7054309,
          geolocation_longitude: -9.3407711,
          name: 'Portagem de Carcavelos'
        }
        const toll = await Toll.create(toll_mock, { transaction: t})

        const user = await User.create({
            nif: 123,
            name: 'John',
            password: 'pw',
            state: 'GOOD'
        }, { transaction: t})

        const vehicle = await Vehicle.create({
            plate: 'AA-00-06',
            category: 1
        }, { transaction: t})
        vehicle.setFK_nif(user.nif)

        const trip = await Trip.create({
            state: 'CONFIRMED'
        }, { transaction: t})
        trip.setFK_vehicle(vehicle.id)

        /* Transaction */
        const transaction = await Transaction.create({
            etc_id: 'etc_id',
            toll_id: 1,
            trip_id: 1
        }, {
            transaction: t,
            include: [
                {
                    association: Transaction.Toll
                },{
                    association: Transaction.Trip
                }]
        })
        //toll.addTrip(trip)
        //trip.addToll(toll)

        await t.commit()
        const fetch = await Transaction.findById(transaction.id, {
            include: [{association: Transaction.Trip}, {association: Transaction.Toll}]
        })
        
        expect(fetch.etc_id).to.be.equal(transaction.etc_id)
        //await toll.destroy()
    }catch(err){
        console.error(err)
        await t.rollback()
        throw err
    }

  })
})