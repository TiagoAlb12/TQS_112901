import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { CarouselDemo } from './components/Carousel';
import { RestaurantsPage } from './pages/RestaurantsPage';
import { ReservationsPage } from './pages/ReservationPage';
import { StaffPage } from './pages/StaffPage';

function App() {
  return (
    <Router>
      {/* Título fixo */}
      <div className="w-full h-20 bg-[#4CAF50] fixed top-0 left-0 z-50">
        <div className="flex justify-center items-center h-full text-white text-4xl font-bold">
          Moliceiro University campus
        </div>
      </div>

      {/* Conteúdo com margem para não ficar atrás da barra */}
      <div>
        <Routes>
          <Route path="/" element={<CarouselDemo />} />
          <Route path="/restaurants" element={<RestaurantsPage />} />
          <Route path="/reservas" element={<ReservationsPage />} />
          <Route path="/staff" element={<StaffPage />} />
        </Routes>
      </div>

      {/* Footer */}
      <footer className="footer">
        <div className="cadeira-nome">TQS_112901 - HW1</div>
        <div className="center-name">Moliceiro University</div>
        <div className="staff-link">
          <a href="/staff">Staff</a>
        </div>
      </footer>
    </Router>
  );
}

export default App;
