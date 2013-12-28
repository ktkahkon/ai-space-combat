package org.spacecombat

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.mockito.Matchers.anyDouble

class SpaceCraftSpec extends FlatSpec with Matchers with MockitoSugar {
  "A SpaceCraft update" should "increase the craft's energy level" in {
    val craft = new SpaceCraft(maxEnergy = 1800.0, energyRecoveryRate = 2.0, turnRate = 60.0, accelerationRate = 10.0)
    craft.energy = 100.0
    craft.update()
    craft.energy should be (102.0)
  }

  it should "not allow energy level to rise over maximum level" in {
    val maxEnergy = 1800.0
    val craft = new SpaceCraft(maxEnergy, energyRecoveryRate = 2.0, turnRate = 60.0, accelerationRate = 10.0)
    craft.energy = maxEnergy
    craft.update()
    craft.energy should be (maxEnergy)
  }

  it should "update the velocity when thruster is on" in {
    val craft = new SpaceCraft(maxEnergy = 1800.0, energyRecoveryRate = 2.0, turnRate = 60.0, accelerationRate = 10.0)
    val velocityMock = mock[Velocity]
    craft.velocity = velocityMock
    craft.mainThrustersAreOn = true
    craft.update()
    verify(velocityMock).update(anyDouble, anyDouble)
  }

  it should "not update the velocity when thruster is off" in {
    val craft = new SpaceCraft(maxEnergy = 1800.0, energyRecoveryRate = 2.0, turnRate = 60.0, accelerationRate = 10.0)
    val velocityMock = mock[Velocity]
    craft.velocity = velocityMock
    craft.mainThrustersAreOn = false
    craft.update()
    verifyZeroInteractions(velocityMock)
  }
}
