<template>
  <b-container
    class="bv-example-row"
    fluid
    style="background-color: #3323; height: 100vh"
  >
    <b-row>
      <b-col sm="6" offset="3">
        <hr class="line" />
        <b-jumbotron class="jump" header="Create a new account">
          <hr />
          <b-form @submit="onSubmit" @reset="onReset" v-if="show">
            <label class="title">Email: </label>
            <b-form-input
              class="input"
              type="email"
              id="input-1"
              v-model="form.username"
              placeholder="Enter email"
              required
            ></b-form-input>

            <label class="title"> Mobile phone: </label>
            <b-form-input
              class="input"
              id="input-123123"
              v-model="form.phonenumber"
              placeholder="Enter your mobile number"
              required
            ></b-form-input>

            <b-form @submit.stop.prevent>
              <label for="text-password" class="title">Password:</label>
              <b-form-input
                type="password"
                id="text-password"
                v-model="form.password"
                aria-describedby="password-help-block"
                class="input"
                placeholder="Enter Password"
              ></b-form-input>
            </b-form>

            <b-form @submit.stop.prevent>
              <label for="text-password" class="title">Retype password:</label>
              <b-form-input
                type="password"
                id="text-password1"
                v-model="password2"
                aria-describedby="password-help-block"
                class="input"
                placeholder="Enter Password"
              ></b-form-input>
            </b-form>

            <label class="title">Gender: </label>

            <b-form-select
              class="input"
              id="input-123"
              v-model="form.gender"
              :options="genders"
              :value="form.gender"
              required
            ></b-form-select>

            <label class="title"> Age: </label>
            <b-form-input
              class="input"
              id="input-123123"
              v-model="form.age"
              placeholder="Enter your age"
              required
            ></b-form-input>
            <br />
            <b-button class="btn" type="submit" variant="primary"
              >Sign Up</b-button
            >
            <hr />
          </b-form>
          <a href="#/"> You already have an account? sign in now. </a>
        </b-jumbotron>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
  data() {
    return {
      checkState: true,
      password2: "",
      form: {
        username: "",
        password: "",
        age: "",
        phonenumber: "",
        gender: " ",
      },
      genders: [{ text: "Choose one", value: " " }, "Male", "Female"],
      show: true,
    };
  },
  methods: {
    async onSubmit(event) {
      event.preventDefault();
      if (this.form.password !== this.password2) {
        alert("please make sure that the two passwords match");
        return;
      }
      await fetch("http://localhost:8085/" + this.form.username,{
        method: "get",
      })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.checkState = data;
        });

      if (this.checkState === false) {
        //fetch here
        await fetch("http://localhost:8085/", {
          method: "post",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(this.form),
        });
        alert("Signed up seccessfully");
        this.$router.push({ name: "signIn" });
      } else {
        alert("Username is already taken");
        return;
      }
    },
    onReset(event) {
      event.preventDefault();
      // Reset our form values
      this.form.username = "";
      // Trick to reset/clear native browser form validation state
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
  },
};
</script>

<style scoped>
.sptitle {
  display: inline-block;
  width: 20%;
  text-align: center;
  margin-left: 0px;
  padding: 0;
  font-weight: bold;
}
.calender {
  display: inline-block !important;
  width: 25%;
  margin-left: 310px;
  height: 10px;
}
.title {
  text-align: left;
  display: inline-block;
  font-size: 14px;
  margin-right: 20px;
  width: 20%;
  font-weight: bold;
}
.input {
  display: inline-block;
  margin-bottom: 20px;
  width: 70%;
}
.btn {
  margin: 10px;
}
.ps-title {
  display: block;
  text-align: left;
}
.ps-input {
  width: 50%;
  margin-bottom: 20px;
}
.header-text {
  font-size: 50px;
  margin-bottom: 40px;
}
.line {
  margin-bottom: 50px;
}
.btn {
  margin-bottom: 0px;
}
</style>


