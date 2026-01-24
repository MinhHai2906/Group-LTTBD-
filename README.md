# AppBanNuoc - Ứng Dụng Bán Nước

Ứng dụng Android hiện đại cho phép khách hàng mua sắm nước trực tuyến với giao diện thân thiện và nhiều tính năng tiện lợi.

## 📸 Demo

![App Demo](demo-screenshot.png)

---

## 🎯 Tính Năng Chính

### 🛍️ Duyệt & Tìm Kiếm Sản Phẩm
- Danh sách sản phẩm nước đầy đủ với hình ảnh, giá cả và mô tả
- Tìm kiếm nhanh theo tên sản phẩm
- Lọc sản phẩm theo loại, giá cả, đánh giá
- Hiển thị chi tiết sản phẩm với các thông tin đầy đủ

### 🛒 Quản Lý Giỏ Hàng
- Thêm/xóa sản phẩm vào giỏ hàng
- Chỉnh sửa số lượng sản phẩm
- Tính tổng giá tự động
- Xóa toàn bộ giỏ hàng
- Lưu giỏ hàng cục bộ

### 📦 Đặt Hàng & Thanh Toán
- Tạo đơn hàng từ giỏ hàng
- Chọn địa chỉ giao hàng
- Chọn phương thức thanh toán
- Theo dõi trạng thái đơn hàng
- Lịch sử đơn hàng

### 👤 Quản Lý Tài Khoản
- Đăng ký/Đăng nhập
- Xem và chỉnh sửa thông tin cá nhân
- Quản lý địa chỉ giao hàng
- Lịch sử mua hàng
- Yêu thích sản phẩm

### ⭐ Đánh Giá & Bình Luận
- Xem đánh giá sản phẩm từ người dùng khác
- Gửi đánh giá và bình luận cho sản phẩm
- Xem bình luận trên chi tiết sản phẩm

### 📱 Giao Diện Thân Thiện
- Giao diện đẹp mắt, dễ sử dụng
- Hỗ trợ Dark Mode
- Responsive design
- Tương thích tất cả các kích thước màn hình

---

## 🛠️ Tech Stack

**Language:** Kotlin 100%  
**UI:** Jetpack Compose (Material 3) / XML Layout  
**Architecture:** MVVM + Repository Pattern  
**DI:** Dagger Hilt / Koin  

**Database:** Room (SQLite)  
**Networking:** Retrofit2 + OkHttp3  
**Authentication:** Firebase Auth / JWT  

**Image Loading:** Glide / Coil  
**State Management:** ViewModel + LiveData / StateFlow  

---

## 📁 Cấu Trúc Dự Án

```
app/src/main/java/com/example/appbannuoc/
├── MainActivity.kt                      # Entry point
│
├── ui/
│   ├── screens/
│   │   ├── HomeScreen.kt               # Trang chủ
│   │   ├── ProductListScreen.kt        # Danh sách sản phẩm
│   │   ├── ProductDetailScreen.kt      # Chi tiết sản phẩm
│   │   ├── CartScreen.kt               # Giỏ hàng
│   │   ├── CheckoutScreen.kt           # Thanh toán
│   │   ├── OrderHistoryScreen.kt       # Lịch sử đơn hàng
│   │   ├── OrderDetailScreen.kt        # Chi tiết đơn hàng
│   │   ├── AccountScreen.kt            # Tài khoản
│   │   ├── LoginScreen.kt              # Đăng nhập
│   │   └── RegisterScreen.kt           # Đăng ký
│   │
│   ├── viewmodels/
│   │   ├── ProductViewModel.kt
│   │   ├── CartViewModel.kt
│   │   ├── OrderViewModel.kt
│   │   └── AccountViewModel.kt
│   │
│   └── components/
│       ├── ProductCard.kt
│       ├── CartItem.kt
│       └── OrderItem.kt
│
├── data/
│   ├── local/
│   │   ├── database/
│   │   │   ├── AppDatabase.kt          # Room Database
│   │   │   ├── dao/
│   │   │   │   ├── ProductDao.kt
│   │   │   │   ├── CartDao.kt
│   │   │   │   └── OrderDao.kt
│   │   │   └── entities/
│   │   │       ├── ProductEntity.kt
│   │   │       ├── CartEntity.kt
│   │   │       └── OrderEntity.kt
│   │   │
│   │   └── preferences/
│   │       └── DataStore.kt            # SharedPreferences / DataStore
│   │
│   ├── remote/
│   │   ├── api/
│   │   │   ├── ApiService.kt           # Retrofit API
│   │   │   ├── ApiResponse.kt          # API Response models
│   │   │   └── interceptor/
│   │   │       └── AuthInterceptor.kt
│   │   │
│   │   └── models/
│   │       ├── ProductResponse.kt
│   │       ├── OrderResponse.kt
│   │       └── UserResponse.kt
│   │
│   ├── repository/
│   │   ├── ProductRepository.kt
│   │   ├── CartRepository.kt
│   │   ├── OrderRepository.kt
│   │   └── AuthRepository.kt
│   │
│   └── models/
│       ├── Product.kt
│       ├── Cart.kt
│       ├── Order.kt
│       └── User.kt
│
├── di/
│   ├── NetworkModule.kt
│   ├── DatabaseModule.kt
│   └── RepositoryModule.kt
│
└── utils/
    ├── Constants.kt
    ├── Extensions.kt
    └── Validators.kt
```

---

## 📋 Models & Entities

### Product Model
```kotlin
data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val image: String,
    val category: String,
    val description: String,
    val rating: Float,
    val stock: Int
)
```

### Cart Model
```kotlin
data class CartItem(
    val id: String,
    val productId: String,
    val quantity: Int,
    val price: Double
)
```
### Order Model
```kotlin
data class Order(
    val id: String,
    val userId: String,
    val items: List<CartItem>,
    val total: Double,
    val status: String,
    val createdAt: Long,
    val deliveryAddress: String
)
```

---

## 🚀 Hướng Dẫn Cài Đặt & Chạy Dự Án

### 📋 Chuẩn Bị Môi Trường

- **Android Studio Hedgehog+**
- **JDK 11+**
- **Gradle 8.0+**

### 🏗️ Build & Run

```bash
# Cloning project
git clone <repo-url>
cd AppBanNuoc

# Build project
./gradlew build

# Run trên emulator hoặc device
./gradlew installDebug
```

### 📦 Build Production

```bash
# Build release APK
./gradlew assembleRelease

# Output: app/build/outputs/apk/release/app-release.apk
```

---

## 📖 Hướng Dẫn Sử Dụng

### 🔐 Đăng Nhập/Đăng Ký
1. Mở ứng dụng
2. Nhấn "Đăng Ký" để tạo tài khoản mới
3. Nhập email, mật khẩu và thông tin cá nhân
4. Xác nhận đăng ký thành công

### 🛍️ Mua Sắm
1. Duyệt danh sách sản phẩm trên trang chủ
2. Chọn sản phẩm để xem chi tiết
3. Nhấn "Thêm vào giỏ hàng" để mua
4. Điều chỉnh số lượng nếu cần

### 🛒 Thanh Toán
1. Mở giỏ hàng
2. Kiểm tra sản phẩm và giá cả
3. Nhấn "Thanh Toán"
4. Chọn địa chỉ giao hàng
5. Chọn phương thức thanh toán
6. Xác nhận đặt hàng

### 📦 Theo Dõi Đơn Hàng
1. Vào mục "Lịch sử đơn hàng"
2. Chọn đơn hàng cần xem
3. Theo dõi trạng thái giao hàng

---

## 🔗 API Endpoints

```
GET    /api/products              # Lấy danh sách sản phẩm
GET    /api/products/:id          # Chi tiết sản phẩm
POST   /api/cart                  # Thêm vào giỏ hàng
GET    /api/cart                  # Lấy giỏ hàng
DELETE /api/cart/:id              # Xóa khỏi giỏ hàng
POST   /api/orders                # Tạo đơn hàng
GET    /api/orders                # Lịch sử đơn hàng
GET    /api/orders/:id            # Chi tiết đơn hàng
POST   /api/auth/register         # Đăng ký
POST   /api/auth/login            # Đăng nhập
GET    /api/user                  # Thông tin tài khoản
```

---

## 📝 License

Dự án này được phát triển cho mục đích học tập.

---

## 👥 Tác Giả

AppBanNuoc Team - Ứng Dụng Bán Nước Trực Tuyến