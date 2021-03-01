<template>
  <div class="Content">
    <div class="top">
      <div class="FontStyle">菜单导航</div>
      <div class="imgBox">
        <img
          class="topImg"
          src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/backNavigation3xZpq.png"
          alt=""
        />
      </div>
    </div>
    <div class="MiddlePic">
      <img
        class="BigPic"
        src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/backbanner3xZpq.png"
        alt=""
      />
    </div>
    <div class="Bottom">
      <div class="Items">
        <div class="ItemBackGround">
          <div class="ItemBox">
          <div class="ItemLeft">
              <img class="Logo" src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/backCabinetLogo3xZpq.png" alt="">
          </div>
          <div class="ItemRight">
             <div class="RightTxt">

             </div>
          </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
};
</script>

<style lang="scss" scoped>
.Content {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100vw;
  .top {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 90%;
    padding: 15px 0;
    .FontStyle {
      font-size: 17px;
      color: #333333;
    }
    .imgBox {
      display: flex;
      justify-content: center;
      align-items: center;
      .topImg {
        width: 26px;
        height: 18px;
      }
    }
  }
  .MiddlePic {
    display: flex;
    justify-content: center;
    align-items: center;
    .BigPic {
      width: 100%;
      height: 160px;
    }
  }
  .Bottom {
    width: 100%;
    .Items {
      padding: 22px 15px 0;

        .ItemBackGround {
          background-image: url("https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/Cabinet%20management3xZpq.png");
          background-size: contain;
          height: 5rem;
          background-repeat: no-repeat;
          display: flex;
          justify-content: center;
          align-items: center;
          .ItemBox {
              display: flex;
              align-items: center;
              justify-content: space-between;
              width: 90%;
              .ItemLeft{
                  display: flex;
                  justify-content: center;
                  align-items: center;
                  .Logo{
                   width: 26px;
                  height: 38px;
                  }
              }
              .ItemRight{
                  display: flex;
                  justify-content: center;
                  align-items: center;
                  .RightTxt{
                      display: flex;
                      flex-direction: column;
                      align-items: flex-start;
                  }
              }
        }
      }
    }
  }
}
</style>
