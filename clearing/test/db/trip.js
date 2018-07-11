/* var expect = require('chai').expect;
const Sequelize = require('sequelize')
const setup = require('../../utils/db/setup')

const ENTITY = 'Trip'

describe('DB' + ENTITY + 'Tests', function () {
  it('insert new ' + ENTITY, async function () {

    var sequelize = await setup()
    const t = await sequelize.transaction({
        isolationLevel: Sequelize.Transaction.ISOLATION_LEVELS.SERIALIZABLE
    })

    var {Vehicle, User, Trip} = sequelize.entities
    //await sequelize.sync()
    // 1. ARRANGE
    try{
        
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

        await t.commit()
        const fetch = await Trip.findById(trip.id)
        expect(fetch.state).to.be.equal('CONFIRMED')
        
        trip.destroy()
        vehicle.destroy()
        user.destroy()
    }catch(err){
        console.error(err)
        await t.rollback()
        throw err
    }
    // 2. ACT

    // 3. ASSERT
    //expect(curr.plate).to.be.equal(mock.plate)
  })
}) */