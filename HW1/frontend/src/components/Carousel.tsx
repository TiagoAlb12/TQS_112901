import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import "./Carousel.css"

import img1 from "../assets/images/cantina5.jpg"
import img2 from "../assets/images/cantina.jpg"
import img3 from "../assets/images/cantina2.jpg"
import img4 from "../assets/images/cantina3.jpg"
import img5 from "../assets/images/cantina4.jpg"

export function CarouselDemo() {
  const [current, setCurrent] = useState(0)
  const total = 5
  const navigate = useNavigate()

  const images = [img1, img2, img3, img4, img5]

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrent(prev => (prev === total - 1 ? 0 : prev + 1))
    }, 5000)

    return () => clearInterval(interval)
  }, [total])

  return (
    <div className="carousel">
      <div className="overlay-box">
        <button className="enter-button" onClick={() => navigate("/restaurants")}>
          Ver Menus
        </button>
      </div>
      <div
        className="carousel-inner"
        style={{ transform: `translateX(-${current * 100}vw)` }}
      >
        {images.map((src, index) => (
          <div className="carousel-item" key={index}>
            <div className="card">
              <img
                src={src}
                alt={`Slide ${index + 1}`}
                className="card-image"
              />
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}
