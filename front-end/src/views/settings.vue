<template >
  <b-container
    class="bv-example-row"
    fluid
    style="background-color: #3323; height: 140vh"
  >
    <b-row>
      <b-col sm="6" offset="3">
        <hr class="line" />
        <b-jumbotron class="jump" header="Edit account">
          <hr />
          <h2 style="text-align: center">{{ form.username }}</h2>
          <b-form @submit="onSubmit" @reset="onReset" v-if="show">
            <label class="title"> Mobile phone: </label>
            <b-form-input
              class="input"
              id="input-2"
              v-model="form.phonenumber"
              placeholder="Enter your mobile number"
              required
            ></b-form-input>

            <b-form @submit.stop.prevent>
              <label for="text-password" class="title">New password </label>
              <b-form-input
                type="password"
                id="text-password"
                v-model="form.password"
                aria-describedby="password-help-block"
                class="input"
                placeholder="Enter Password"
                required
              ></b-form-input>
            </b-form>

            <b-form @submit.stop.prevent>
              <label for="text-password" class="title">Retype password</label>
              <b-form-input
                type="password"
                id="text-password"
                v-model="password2"
                aria-describedby="password-help-block"
                class="input"
                placeholder="Enter Password"
                required
              ></b-form-input>
            </b-form>

            <label class="title">Gender: </label>
            <b-form-select
              id="input-3"
              class="input"
              v-model="form.gender"
              :options="genders"
              required
            ></b-form-select>
            <label class="title">Age: </label>
            <b-form-input
              class="input"
              id="input-2123123123"
              v-model="form.age"
              placeholder="Enter your age"
              required
            ></b-form-input>
            <br />
            <b-button class="btn" type="submit" variant="primary" @click="next"
              >Save</b-button
            >
            <b-button class="btn" variant="danger" @click="goBack"
              >Don't save</b-button
            >
            <hr />
            <b-button
              class="btn"
              type="submit"
              variant="danger"
              @click="showMsgBoxTwo"
              >Delete Account</b-button
            >
          </b-form>
        </b-jumbotron>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
  name: "settings",
  data() {
    return {
      password2: "",
      form: {
        username: "",
        gender: "Male",
        phonenumber: "",
        password: "",
        age: "",
      },
      genders: [
        { text: "Select One", value: null},
        { text: "Male", value: "Male"},
        { text: "Female", value: "Female"},
      ],
      show: true,
      checkState: true,
      boxTwo: false,
    };
  },
  methods: {
    showMsgBoxTwo() {
      this.boxTwo = false;
      this.$bvModal
        .msgBoxConfirm("Are you sure that you want to delete this account?", {
          title: "WARNING!!",
          size: "sm",
          buttonSize: "sm",
          okVariant: "danger",
          okTitle: "YES",
          cancelTitle: "NO",
          footerClass: "p-2",
          hideHeaderClose: false,
          centered: true,
        })
        .then((value) => {
          this.boxTwo = value;
          if (this.boxTwo == true) {
            this.delete();
          }
          this.boxTwo = "";
        })
        .catch((err) => {
          // An error occurred
        });
    },
    onSubmit(event) {},
    onReset(event) {},
    goBack() {
      this.$router.push({ name: "signedIn" });
    },
    async next(event) {
      event.preventDefault();
      if (this.password2 !== this.form.password) {
        alert("Please make sure that the two passwords match");
        return;
      }
            if (this.password2 == '') {
        alert("passowrd can not be empty");
        return;
      }
      //check here .. fetch
      await fetch("http://localhost:8085/updateSetting", {
        method: "post",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(this.form),
      });
      if (this.checkState == true) {
        alert("Account details have been modified successfully.");
        this.$router.push({ name: "signedIn" });
      } else {
        alert("Username has already taken,try another one");
      }
    },
    async delete() {
      // fetch delete
      await fetch("http://localhost:8085/deleteUser", {
        method: "delete",
      });
      alert("Account has been deleted successfully.");
      this.$router.push({
        name: "signIn",
      });
    },
  },
    async mounted() {
    // fetch the old data here ..
    await fetch("http://localhost:8085/setting", {
      method: "GET",
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        this.form.username = data[0];
        this.form.age = data[1];
        this.form.phonenumber = data[2];
        this.form.gender = data[3];

      });
    console.log("ahahaah", this.form);
    console.log("ahmed");
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


