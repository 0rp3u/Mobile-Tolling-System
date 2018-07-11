var expect = require('chai').expect;
const setup = require('../../utils/db/setup')
function _describe(){}
_describe('DB Vehicle Tests', function () {
  it('insert new vehicle', async function () {
    var sequelize = await setup()
    //console.log(sequelize)
    var {Vehicle, User} = sequelize.entities
    //await sequelize.sync()
    // 1. ARRANGE
    //await Vehicle.sync({force: true})
    //await Vehicle.sync()
    var curr1 = null
    try{
      curr1 = (await User.findAll())[0].dataValues
    }catch(reason){
      console.error(reason)
    }

    const FK_Owner = Vehicle.belongsTo(User, {
      foreignKey: 'nif',
      constraints: false,
      as: 'FK_nif'
    })

/*     const FK_Owner2 = User.hasMany(Vehicle, {
      foreignKey: 'nif',
      constraints: false,
      as: 'FK_nif'
    }) */

    //await sequelize.sync()

    /*     const FK_Owner = Vehicle.belongsTo(User, {
        foreignKey: 'nif',
        constraints: false,
        as: 'FK_nif'
      }) */
    /*    const FK_Owner = User.hasMany(Vehicle, {
        foreignKey: 'commentable_id',
        constraints: false,
        scope: {
          commentable: 'post'
        }
      }) */
      const _id = 20
      const mock = {
          //id: _id,
          plate: 'AA-00-05',
          category: 1
      }

      //await Vehicle.create(mock, { include: [FK_Owner]})
      var v = null
      try{
        v = await Vehicle.create(mock)
      }catch(reason){
        console.error(reason)
        throw reason
      }
      
      v.setFK_nif(123)
      //Vehicle.createFK_nif(123)
      
      const curr = await Vehicle.findById(v.id)
      // 2. ACT
      //const curr = (await Vehicle.findAll())[0].dataValues

      // 3. ASSERT
      expect(curr.plate).to.be.equal(mock.plate)
  })
})