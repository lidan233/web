<template>


  <div id="app" style="height: 100%">
    <div id="particlesId" style="height:800px;width: 100%">
      <vue-particles
        color="#dedede"
        :particleOpacity="0.7"
        :particlesNumber="80"
        shapeType="circle"
        :particleSize="4"
        linesColor="#dedede"
        :linesWidth="2"
        :lineLinked="true"
        :lineOpacity="0.4"
        :linesDistance="150"
        :moveSpeed="3"
        :hoverEffect="true"
        hoverMode="grab"
        :clickEffect="true"
        clickMode="push"
      >
      </vue-particles>
    </div>
    <router-view/>
  </div>
</template>

<script>

export default {
  name: 'App'

}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

#particlesId{
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: #2c1d3c;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: 100% 100%;
  z-index: -1;
}

</style>
