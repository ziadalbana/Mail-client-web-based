<template>
  <b-container class="bv-example-row" fluid style="background-color: #3323; height:100vh;">
    <b-row>
      <b-col sm="6" offset="3">
        <hr class="line" />
        <b-jumbotron header="Sign in">
          <b-form
            class="parent"
            @submit="onSubmit"
            @reset="onReset"
            v-if="show"
          >
            <label class="title">Email:</label>
            <b-form-input
              class="input"
              id="input-1"
              v-model="form.username"
              type="email"
              placeholder="Enter your email"
              required
            ></b-form-input>

            <label class="title">Password:</label>
            <b-form-input
              type="password"
              id="text-password"
              v-model="form.password"
              aria-describedby="password-help-block"
              class="input"
              placeholder="Enter Password"
              required
            ></b-form-input>

            <b-button class="btn" type="submit" variant="primary">
              Sign in
            </b-button>
            <hr />
          </b-form>
          <a href="#/signUp"> You haven't an account? create one now. </a>
        </b-jumbotron>
        <hr />
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
  name: "signIn",
  data() {
    return {
      form: {
        username: "",
        password: "",
      },
      show: true,
      checkState: false,
    };
  },
  methods: {
    async onSubmit(event) {
      event.preventDefault();
      await fetch(
        "http://localhost:8085/login/" +
          this.form.username +
          "/" +
          this.form.password,
        {
          method: "get",
        }
      )
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          console.log("this is data ", data);
          this.checkState = data;
        });

      console.log("this is check ", this.checkState);
      if (this.checkState === true) {
        this.$router.push({ name: "signedIn" });
        // window.open("#/signedIn");
      } else {
        alert("Please try agian, username or password is wrong :(");
      }
    },
    onReset(event) {
      event.preventDefault();
    },
  },
};
</script>

<style scoped>
.title {
  text-align: center;
  display: inline-block;
  font-size: 16px;
  margin-right: 20px;
  width: 20%;
  font-weight: bold;
}
.input {
  width: 50%;
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
.parent {
  margin-top: 30px;
}
</style>